import Vue from "vue";
import App from "./App.vue";
import router, {resetRouter} from "./router";
import store from "./store";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "./assets/css/index.css";
import "./assets/css/iconfont.css";
import config from "./assets/js/config";
import axios from "axios";
import VueAxios from "vue-axios";
import ECharts from "vue-echarts";
import "echarts/lib/chart/line";
import "echarts/lib/chart/pie";
import "echarts/lib/chart/bar";
import "echarts/lib/chart/map";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import VueCalendarHeatmap from "vue-calendar-heatmap";
import dayjs from "dayjs";
import tagCloud from "./components/tag-cloud";
import Toast from "../../blog/src/components/toast";
Vue.prototype.config = config;
Vue.use(mavonEditor);
Vue.use(tagCloud);
Vue.use(VueCalendarHeatmap);
Vue.component("v-chart", ECharts);
Vue.use(VueAxios, axios);
Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.prototype.$moment = dayjs;
Vue.use(Toast);

Vue.filter("date", function(value, formatStr = "YYYY-MM-DD") {
  return dayjs(value).format(formatStr);
});

Vue.filter("dateTime", function(value, formatStr = "YYYY-MM-DD HH:mm:ss") {
  return dayjs(value).format(formatStr);
});

NProgress.configure({
  easing: "ease", // 动画方式
  speed: 500, // 递增进度条的速度
  showSpinner: false, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3 // 初始化时的最小百分比
});

router.beforeEach((to, from, next) => {
  NProgress.start();
  if (to.path == "/login") {
    next();
  } else if (!store.state.userId) {
    next({ path: "/login" });
  } else {
    next();
  }
});

router.afterEach(() => {
  NProgress.done();
});

//请求拦截器
axios.interceptors.request.use(
  config => {
    // console.log("被拦截！！！！！！！！！！！！！！！！！！！！");
    if (window.sessionStorage.getItem("Authorization")) {
      config.headers.Authorization = window.sessionStorage.getItem(
        "Authorization"
      );
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截器
axios.interceptors.response.use(
  function(response) {
    // console.log("拦截响应！！！！！！！！！！！！");
    switch (response.data.code) {
      case 50000:
        Vue.prototype.$toast({ type: "error", message: "系统异常" });
        break;
      case 60002:
        Vue.prototype.$toast({ type: "error", message: response.data.message });
        //删除用户数据
        window.sessionStorage.removeItem("Authorization");
        store.commit("logout");
        resetRouter();
        router.push({ path: "/login" });
        //重新登陆
        store.state.loginFlag = true;
        console.log("注销了吗？")
        break;
    }
    return response;
  },
  function(error) {
    return Promise.reject(error);
  }
);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
