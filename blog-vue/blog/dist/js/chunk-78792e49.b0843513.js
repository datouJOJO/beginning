(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-78792e49"],{"1a25":function(t,e,n){"use strict";var i=n("c368"),s=n.n(i);s.a},"1e55":function(t,e,n){},"1ec4":function(t,e){t.exports=function(t){var e=t.tocElement||document.querySelector(t.tocSelector);if(e&&e.scrollHeight>e.clientHeight){var n=e.querySelector("."+t.activeListItemClass);n&&(e.scrollTop=n.offsetTop)}}},"2ebb":function(t,e,n){},"2f3e":function(t,e,n){"use strict";var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{ref:"reply",staticClass:"reply-input-wrapper",staticStyle:{display:"none"}},[n("textarea",{directives:[{name:"model",rawName:"v-model",value:t.commentContent,expression:"commentContent"}],staticClass:"comment-textarea",attrs:{placeholder:"回复 @"+t.nickname+"：","auto-grow":"",dense:""},domProps:{value:t.commentContent},on:{input:function(e){e.target.composing||(t.commentContent=e.target.value)}}}),t._v(" "),n("div",{staticClass:"emoji-container"},[n("span",{class:t.chooseEmoji?"emoji-btn-active":"emoji-btn",on:{click:function(e){t.chooseEmoji=!t.chooseEmoji}}},[n("i",{staticClass:"iconfont iconbiaoqing"})]),n("div",{staticStyle:{"margin-left":"auto"}},[n("button",{staticClass:"cancle-btn v-comment-btn",on:{click:t.cancleReply}},[t._v(" 取消 ")]),n("button",{staticClass:"upload-btn v-comment-btn",on:{click:t.insertReply}},[t._v(" 提交 ")])])]),n("emoji",{attrs:{chooseEmoji:t.chooseEmoji},on:{addEmoji:t.addEmoji}})],1)},s=[],a=(n("a9e3"),n("ac1f"),n("5319"),n("1276"),n("498a"),n("bbe3")),r=n("f568"),o={components:{Emoji:a["a"]},props:{type:{type:Number}},data:function(){return{index:0,chooseEmoji:!1,nickname:"",replyUserId:null,parentId:null,commentContent:""}},methods:{cancleReply:function(){this.$refs.reply.style.display="none"},insertReply:function(){var t=this;if(!this.$store.state.userId)return this.$store.state.loginFlag=!0,!1;if(""==this.commentContent.trim())return this.$toast({type:"error",message:"回复不能为空"}),!1;var e=/\[.+?\]/g;this.commentContent=this.commentContent.replace(e,(function(t){return"<img src= '"+r["a"][t]+"' width='24'height='24' style='margin: 0 1px;vertical-align: text-bottom'/>"}));var n=this.$route.path,i=n.split("/"),s={type:this.type,replyUserId:this.replyUserId,parentId:this.parentId,commentContent:this.commentContent};switch(this.type){case 1:case 3:s.topicId=i[2];break;default:break}this.commentContent="",this.axios.post("/api/comments",s).then((function(e){var n=e.data;n.flag?(t.$emit("reloadReply",t.index),t.$toast({type:"success",message:"回复成功"})):t.$toast({type:"error",message:n.message})}))},addEmoji:function(t){this.commentContent+=t}}},c=o,l=(n("5b19"),n("2877")),u=Object(l["a"])(c,i,s,!1,null,"518f661a",null);e["a"]=u.exports},"374a":function(t,e,n){},"5b19":function(t,e,n){"use strict";var i=n("899f"),s=n.n(i);s.a},"5bb8":function(t,e){t.exports={tocSelector:".js-toc",contentSelector:".js-toc-content",headingSelector:"h1, h2, h3",ignoreSelector:".js-toc-ignore",hasInnerContainers:!1,linkClass:"toc-link",extraLinkClasses:"",activeLinkClass:"is-active-link",listClass:"toc-list",extraListClasses:"",isCollapsedClass:"is-collapsed",collapsibleClass:"is-collapsible",listItemClass:"toc-list-item",activeListItemClass:"is-active-li",collapseDepth:0,scrollSmooth:!0,scrollSmoothDuration:420,scrollSmoothOffset:0,scrollEndCallback:function(t){},headingsOffset:1,throttleTimeout:50,positionFixedSelector:null,positionFixedClass:"is-position-fixed",fixedSidebarOffset:"auto",includeHtml:!1,includeTitleTags:!1,onClick:function(t){},orderedList:!0,scrollContainer:null,skipRendering:!1,headingLabelCallback:!1,ignoreHiddenElements:!1,headingObjectCallback:null,basePath:"",disableTocScrollSync:!1}},"609b":function(t,e,n){"use strict";var i=n("1e55"),s=n.n(i);s.a},"65b9":function(t,e,n){"use strict";var i=n("374a"),s=n.n(i);s.a},7577:function(t,e,n){(function(i){var s,a,r;(function(n,i){a=[],s=i(n),r="function"===typeof s?s.apply(e,a):s,void 0===r||(t.exports=r)})("undefined"!==typeof i?i:this.window||this.global,(function(t){"use strict";var e,i,s=n("5bb8"),a={},r={},o=n("83fa"),c=n("a65a"),l=n("1ec4"),u=!!t&&!!t.document&&!!t.document.querySelector&&!!t.addEventListener;if("undefined"!==typeof window||u){var m,d=Object.prototype.hasOwnProperty;return r.destroy=function(){var t=v(a);null!==t&&(a.skipRendering||t&&(t.innerHTML=""),a.scrollContainer&&document.querySelector(a.scrollContainer)?(document.querySelector(a.scrollContainer).removeEventListener("scroll",this._scrollListener,!1),document.querySelector(a.scrollContainer).removeEventListener("resize",this._scrollListener,!1),e&&document.querySelector(a.scrollContainer).removeEventListener("click",this._clickListener,!1)):(document.removeEventListener("scroll",this._scrollListener,!1),document.removeEventListener("resize",this._scrollListener,!1),e&&document.removeEventListener("click",this._clickListener,!1)))},r.init=function(t){if(u){a=p(s,t||{}),this.options=a,this.state={},a.scrollSmooth&&(a.duration=a.scrollSmoothDuration,a.offset=a.scrollSmoothOffset,r.scrollSmooth=n("bbe6").initSmoothScrolling(a)),e=o(a),i=c(a),this._buildHtml=e,this._parseContent=i,this._headingsArray=m,r.destroy();var d=h(a);if(null!==d){var C=v(a);if(null!==C&&(m=i.selectHeadings(d,a.headingSelector),null!==m)){var g=i.nestHeadingsArray(m),y=g.nest;a.skipRendering||e.render(C,y),this._scrollListener=f((function(t){e.updateToc(m),!a.disableTocScrollSync&&l(a);var n=t&&t.target&&t.target.scrollingElement&&0===t.target.scrollingElement.scrollTop;(t&&(0===t.eventPhase||null===t.currentTarget)||n)&&(e.updateToc(m),a.scrollEndCallback&&a.scrollEndCallback(t))}),a.throttleTimeout),this._scrollListener(),a.scrollContainer&&document.querySelector(a.scrollContainer)?(document.querySelector(a.scrollContainer).addEventListener("scroll",this._scrollListener,!1),document.querySelector(a.scrollContainer).addEventListener("resize",this._scrollListener,!1)):(document.addEventListener("scroll",this._scrollListener,!1),document.addEventListener("resize",this._scrollListener,!1));var _=null;return this._clickListener=f((function(t){a.scrollSmooth&&e.disableTocAnimation(t),e.updateToc(m),_&&clearTimeout(_),_=setTimeout((function(){e.enableTocAnimation()}),a.scrollSmoothDuration)}),a.throttleTimeout),a.scrollContainer&&document.querySelector(a.scrollContainer)?document.querySelector(a.scrollContainer).addEventListener("click",this._clickListener,!1):document.addEventListener("click",this._clickListener,!1),this}}}},r.refresh=function(t){r.destroy(),r.init(t||this.options)},t.tocbot=r,r}function p(){for(var t={},e=0;e<arguments.length;e++){var n=arguments[e];for(var i in n)d.call(n,i)&&(t[i]=n[i])}return t}function f(t,e,n){var i,s;return e||(e=250),function(){var a=n||this,r=+new Date,o=arguments;i&&r<i+e?(clearTimeout(s),s=setTimeout((function(){i=r,t.apply(a,o)}),e)):(i=r,t.apply(a,o))}}function h(t){try{return t.contentElement||document.querySelector(t.contentSelector)}catch(e){return console.warn("Contents element not found: "+t.contentSelector),null}}function v(t){try{return t.tocElement||document.querySelector(t.tocSelector)}catch(e){return console.warn("TOC element not found: "+t.tocSelector),null}}}))}).call(this,n("c8ba"))},"83fa":function(t,e){t.exports=function(t){var e,n=[].forEach,i=[].some,s=document.body,a=!0,r=" ";function o(t,e){var n=e.appendChild(l(t));if(t.children.length){var i=u(t.isCollapsed);t.children.forEach((function(t){o(t,i)})),n.appendChild(i)}}function c(t,n){var i=!1,s=u(i);if(n.forEach((function(t){o(t,s)})),e=t||e,null!==e)return e.firstChild&&e.removeChild(e.firstChild),0===n.length?e:e.appendChild(s)}function l(e){var i=document.createElement("li"),s=document.createElement("a");return t.listItemClass&&i.setAttribute("class",t.listItemClass),t.onClick&&(s.onclick=t.onClick),t.includeTitleTags&&s.setAttribute("title",e.textContent),t.includeHtml&&e.childNodes.length?n.call(e.childNodes,(function(t){s.appendChild(t.cloneNode(!0))})):s.textContent=e.textContent,s.setAttribute("href",t.basePath+"#"+e.id),s.setAttribute("class",t.linkClass+r+"node-name--"+e.nodeName+r+t.extraLinkClasses),i.appendChild(s),i}function u(e){var n=t.orderedList?"ol":"ul",i=document.createElement(n),s=t.listClass+r+t.extraListClasses;return e&&(s+=r+t.collapsibleClass,s+=r+t.isCollapsedClass),i.setAttribute("class",s),i}function m(){var n;t.scrollContainer&&document.querySelector(t.scrollContainer)?n=document.querySelector(t.scrollContainer).scrollTop:n=document.documentElement.scrollTop||s.scrollTop;var i=document.querySelector(t.positionFixedSelector);"auto"===t.fixedSidebarOffset&&(t.fixedSidebarOffset=e.offsetTop),n>t.fixedSidebarOffset?-1===i.className.indexOf(t.positionFixedClass)&&(i.className+=r+t.positionFixedClass):i.className=i.className.split(r+t.positionFixedClass).join("")}function d(e){var n=0;return null!==e&&(n=e.offsetTop,t.hasInnerContainers&&(n+=d(e.offsetParent))),n}function p(o){var c;t.scrollContainer&&document.querySelector(t.scrollContainer)?c=document.querySelector(t.scrollContainer).scrollTop:c=document.documentElement.scrollTop||s.scrollTop;t.positionFixedSelector&&m();var l,u=o;if(a&&null!==e&&u.length>0){i.call(u,(function(e,n){if(d(e)>c+t.headingsOffset+10){var i=0===n?n:n-1;return l=u[i],!0}if(n===u.length-1)return l=u[u.length-1],!0}));var p=e.querySelectorAll("."+t.linkClass);n.call(p,(function(e){e.className=e.className.split(r+t.activeLinkClass).join("")}));var h=e.querySelectorAll("."+t.listItemClass);n.call(h,(function(e){e.className=e.className.split(r+t.activeListItemClass).join("")}));var v=e.querySelector("."+t.linkClass+".node-name--"+l.nodeName+'[href="'+t.basePath+"#"+l.id.replace(/([ #;&,.+*~':"!^$[\]()=>|/@])/g,"\\$1")+'"]');v&&-1===v.className.indexOf(t.activeLinkClass)&&(v.className+=r+t.activeLinkClass);var C=v&&v.parentNode;C&&-1===C.className.indexOf(t.activeListItemClass)&&(C.className+=r+t.activeListItemClass);var g=e.querySelectorAll("."+t.listClass+"."+t.collapsibleClass);n.call(g,(function(e){-1===e.className.indexOf(t.isCollapsedClass)&&(e.className+=r+t.isCollapsedClass)})),v&&v.nextSibling&&-1!==v.nextSibling.className.indexOf(t.isCollapsedClass)&&(v.nextSibling.className=v.nextSibling.className.split(r+t.isCollapsedClass).join("")),f(v&&v.parentNode.parentNode)}}function f(e){return e&&-1!==e.className.indexOf(t.collapsibleClass)&&-1!==e.className.indexOf(t.isCollapsedClass)?(e.className=e.className.split(r+t.isCollapsedClass).join(""),f(e.parentNode.parentNode)):e}function h(e){var n=e.target||e.srcElement;"string"===typeof n.className&&-1!==n.className.indexOf(t.linkClass)&&(a=!1)}function v(){a=!0}return{enableTocAnimation:v,disableTocAnimation:h,render:c,updateToc:p}}},"899f":function(t,e,n){},a65a:function(t,e){t.exports=function(t){var e=[].reduce;function n(t){return t[t.length-1]}function i(t){return+t.nodeName.toUpperCase().replace("H","")}function s(e){if(!(e instanceof window.HTMLElement))return e;if(t.ignoreHiddenElements&&(!e.offsetHeight||!e.offsetParent))return null;const n=e.getAttribute("data-heading-label")||(t.headingLabelCallback?String(t.headingLabelCallback(e.textContent)):e.textContent.trim());var s={id:e.id,children:[],nodeName:e.nodeName,headingLevel:i(e),textContent:n};return t.includeHtml&&(s.childNodes=e.childNodes),t.headingObjectCallback?t.headingObjectCallback(s,e):s}function a(e,i){var a=s(e),r=a.headingLevel,o=i,c=n(o),l=c?c.headingLevel:0,u=r-l;while(u>0){if(c=n(o),c&&r===c.headingLevel)break;c&&void 0!==c.children&&(o=c.children),u--}return r>=t.collapseDepth&&(a.isCollapsed=!0),o.push(a),o}function r(e,n){var i=n;t.ignoreSelector&&(i=n.split(",").map((function(e){return e.trim()+":not("+t.ignoreSelector+")"})));try{return e.querySelectorAll(i)}catch(s){return console.warn("Headers not found with selector: "+i),null}}function o(t){return e.call(t,(function(t,e){var n=s(e);return n&&a(n,t.nest),t}),{nest:[]})}return{nestHeadingsArray:o,selectHeadings:r}}},bbe0:function(t,e,n){"use strict";var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"paging"},[n("a",{directives:[{name:"show",rawName:"v-show",value:1!=t.current,expression:"current != 1"}],staticClass:"ml-1 mr-1",on:{click:t.prePage}},[t._v("上一页")]),t.totalPage<6?t._l(t.totalPage,(function(e){return n("a",{key:e,class:"ml-1 mr-1 "+t.isActive(e),on:{click:function(n){return t.changeReplyCurrent(e)}}},[t._v(" "+t._s(e)+" ")])})):t.current<3?[t._l(4,(function(e){return n("a",{key:e,class:"ml-1 mr-1 "+t.isActive(e),on:{click:function(n){return t.changeReplyCurrent(e)}}},[t._v(" "+t._s(e)+" ")])})),n("span",{staticClass:"ml-1 mr-1"},[t._v("···")]),n("a",{staticClass:"ml-1 mr-1",on:{click:function(e){return t.changeReplyCurrent(t.totalPage)}}},[t._v(" "+t._s(t.totalPage)+" ")])]:t.current<5?[t._l(t.current+2,(function(e){return n("a",{key:e,class:"ml-1 mr-1 "+t.isActive(e),on:{click:function(n){return t.changeReplyCurrent(e)}}},[t._v(" "+t._s(e)+" ")])})),t.current+2<t.totalPage-1?n("span",{staticClass:"ml-1 mr-1"},[t._v("···")]):t._e(),t.current+2<t.totalPage?n("a",{staticClass:"ml-1 mr-1",on:{click:function(e){return t.changeReplyCurrent(t.totalPage)}}},[t._v(" "+t._s(t.totalPage)+" ")]):t._e()]:t.current>t.totalPage-2?[n("a",{staticClass:"ml-1 mr-1",on:{click:function(e){return t.changeReplyCurrent(1)}}},[t._v("1")]),n("span",{staticClass:"ml-1 mr-1"},[t._v("···")]),t._l(4,(function(e){return n("a",{key:e,class:"ml-1 mr-1 "+t.isActive(e+(t.totalPage-4)),on:{click:function(n){t.changeReplyCurrent(e+(t.totalPage-4))}}},[t._v(" "+t._s(e+(t.totalPage-4))+" ")])}))]:t.current>t.totalPage-4?[n("a",{staticClass:"ml-1 mr-1",on:{click:function(e){return t.changeReplyCurrent(1)}}},[t._v("1")]),n("span",{staticClass:"ml-1 mr-1"},[t._v("···")]),t._l(t.totalPage-t.current+3,(function(e){return n("a",{key:e,class:"ml-1 mr-1 "+t.isActive(e+t.current-3),on:{click:function(n){return t.changeReplyCurrent(e+t.current-3)}}},[t._v(" "+t._s(e+t.current-3)+" ")])}))]:[n("a",{staticClass:"ml-1 mr-1",on:{click:function(e){return t.changeReplyCurrent(1)}}},[t._v("1")]),n("span",{staticClass:"ml-1 mr-1"},[t._v("···")]),n("a",{staticClass:"ml-1 mr-1",on:{click:function(e){return t.changeReplyCurrent(t.current-2)}}},[t._v(" "+t._s(t.current-2)+" ")]),n("a",{staticClass:"ml-1 mr-1",on:{click:function(e){return t.changeReplyCurrent(t.current-1)}}},[t._v(" "+t._s(t.current-1)+" ")]),n("a",{staticClass:"active ml-1 mr-1"},[t._v(t._s(t.current))]),n("a",{staticClass:"ml-1 mr-1",on:{click:function(e){return t.changeReplyCurrent(t.current+1)}}},[t._v(" "+t._s(t.current+1)+" ")]),n("a",{staticClass:"ml-1 mr-1",on:{click:function(e){return t.changeReplyCurrent(t.current+2)}}},[t._v(" "+t._s(t.current+2)+" ")]),n("span",{staticClass:"ml-1 mr-1"},[t._v("···")]),n("a",{staticClass:"ml-1 mr-1",on:{click:function(e){return t.changeReplyCurrent(t.totalPage)}}},[t._v(t._s(t.totalPage))])],n("a",{directives:[{name:"show",rawName:"v-show",value:t.current!=t.totalPage,expression:"current != totalPage"}],staticClass:"ml-1 mr-1",on:{click:t.nextPage}},[t._v(" 下一页 ")])],2)},s=[],a=(n("a9e3"),{props:{totalPage:{type:Number},index:{type:Number},commentId:{type:Number}},data:function(){return{current:1}},methods:{changeReplyCurrent:function(t){this.current=t,this.$emit("changeReplyCurrent",this.current,this.index,this.commentId)},prePage:function(){this.current-=1,this.$emit("changeReplyCurrent",this.current,this.index,this.commentId)},nextPage:function(){this.current+=1,this.$emit("changeReplyCurrent",this.current,this.index,this.commentId)}},computed:{isActive:function(){return function(t){if(t==this.current)return"active"}}}}),r=a,o=(n("1a25"),n("2877")),c=Object(o["a"])(r,i,s,!1,null,"5811e27a",null);e["a"]=c.exports},bbe6:function(t,e){function n(t){var e=t.duration,n=t.offset,s=location.hash?o(location.href):location.href;function a(){function s(s){!r(s.target)||s.target.className.indexOf("no-smooth-scroll")>-1||"#"===s.target.href.charAt(s.target.href.length-2)&&"!"===s.target.href.charAt(s.target.href.length-1)||-1===s.target.className.indexOf(t.linkClass)||i(s.target.hash,{duration:e,offset:n,callback:function(){c(s.target.hash)}})}document.body.addEventListener("click",s,!1)}function r(t){return"a"===t.tagName.toLowerCase()&&(t.hash.length>0||"#"===t.href.charAt(t.href.length-1))&&(o(t.href)===s||o(t.href)+"#"===s)}function o(t){return t.slice(0,t.lastIndexOf("#"))}function c(t){var e=document.getElementById(t.substring(1));e&&(/^(?:a|select|input|button|textarea)$/i.test(e.tagName)||(e.tabIndex=-1),e.focus())}a()}function i(t,e){var n,i,s=window.pageYOffset,a={duration:e.duration,offset:e.offset||0,callback:e.callback,easing:e.easing||m},r=document.querySelector('[id="'+decodeURI(t).split("#").join("")+'"]')||document.querySelector('[id="'+t.split("#").join("")+'"]'),o="string"===typeof t?a.offset+(t?r&&r.getBoundingClientRect().top||0:-(document.documentElement.scrollTop||document.body.scrollTop)):t,c="function"===typeof a.duration?a.duration(o):a.duration;function l(t){i=t-n,window.scrollTo(0,a.easing(i,s,o,c)),i<c?requestAnimationFrame(l):u()}function u(){window.scrollTo(0,s+o),"function"===typeof a.callback&&a.callback()}function m(t,e,n,i){return t/=i/2,t<1?n/2*t*t+e:(t--,-n/2*(t*(t-2)-1)+e)}requestAnimationFrame((function(t){n=t,l(t)}))}e.initSmoothScrolling=n},c368:function(t,e,n){},d69f:function(t,e,n){"use strict";var i=n("2ebb"),s=n.n(i);s.a},fa20:function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("div",{staticClass:"banner",style:t.articleCover},[n("div",{staticClass:"article-info-container"},[n("div",{staticClass:"article-title"},[t._v(t._s(t.article.articleTitle))]),n("div",{staticClass:"article-info"},[n("div",{staticClass:"first-line"},[n("span",[n("i",{staticClass:"iconfont iconrili"}),t._v(" 发表于 "+t._s(t._f("date")(t.article.createTime))+" ")]),n("span",{staticClass:"separator"},[t._v("|")]),n("span",[n("i",{staticClass:"iconfont icongengxinshijian"}),t._v(" 更新于 "),t.article.updateTime?[t._v(" "+t._s(t._f("date")(t.article.updateTime))+" ")]:[t._v(" "+t._s(t._f("date")(t.article.createTime))+" ")]],2),n("span",{staticClass:"separator"},[t._v("|")]),n("span",{staticClass:"article-category"},[n("i",{staticClass:"iconfont iconfenlei1"}),n("router-link",{attrs:{to:"/categories/"+t.article.categoryId}},[t._v(" "+t._s(t.article.categoryName)+" ")])],1)]),n("div",{staticClass:"second-line"},[n("span",[n("i",{staticClass:"iconfont iconzishu"}),t._v(" 字数统计: "+t._s(t._f("num")(t.wordNum))+" ")]),n("span",{staticClass:"separator"},[t._v("|")]),n("span",[n("i",{staticClass:"iconfont iconshijian"}),t._v(" 阅读时长: "+t._s(t.readTime)+" ")])]),n("div",{staticClass:"third-line"},[n("span",{staticClass:"separator"},[t._v("|")]),n("span",[n("i",{staticClass:"iconfont iconliulan"}),t._v(" 阅读量: "+t._s(t.article.viewsCount)+" ")]),n("span",{staticClass:"separator"},[t._v("|")]),n("span",[n("i",{staticClass:"iconfont iconpinglunzu1"}),t._v("评论数: "+t._s(t.commentCount)+" ")])])])])]),n("v-row",{staticClass:"article-container"},[n("v-col",{attrs:{md:"9",cols:"12"}},[n("v-card",{staticClass:"article-wrapper"},[n("article",{ref:"article",staticClass:"article-content markdown-body",attrs:{id:"write"},domProps:{innerHTML:t._s(t.article.articleContent)}}),n("div",{staticClass:"aritcle-copyright"},[n("div",[n("span",[t._v("文章作者：")]),n("router-link",{attrs:{to:"/"}},[t._v(" "+t._s(t.blogInfo.websiteConfig.websiteAuthor)+" ")])],1),n("div",[n("span",[t._v("文章链接：")]),n("a",{attrs:{href:t.articleHref,target:"_blank"}},[t._v(t._s(t.articleHref)+" ")])]),n("div",[n("span",[t._v("版权声明：")]),t._v("本博客所有文章除特别声明外，均采用 "),n("a",{attrs:{href:"https://creativecommons.org/licenses/by-nc-sa/4.0/",target:"_blank"}},[t._v(" CC BY-NC-SA 4.0 ")]),t._v(" 许可协议。转载请注明文章出处。 ")])]),n("div",{staticClass:"article-operation"},[n("div",{staticClass:"tag-container"},t._l(t.article.tagDTOList,(function(e){return n("router-link",{key:e.id,attrs:{to:"/tags/"+e.id}},[t._v(" "+t._s(e.tagName)+" ")])})),1),n("share",{staticStyle:{"margin-left":"auto"},attrs:{config:t.config}})],1),n("div",{staticClass:"article-reward"},[n("a",{class:t.isLike,on:{click:t.like}},[n("v-icon",{attrs:{size:"14",color:"#fff"}},[t._v("mdi-thumb-up")]),t._v(" 点赞 "),n("span",{directives:[{name:"show",rawName:"v-show",value:t.article.likeCount>0,expression:"article.likeCount > 0"}]},[t._v(t._s(t.article.likeCount))])],1),1==t.blogInfo.websiteConfig.isReward?n("a",{staticClass:"reward-btn"},[n("i",{staticClass:"iconfont iconerweima"}),t._v(" 打赏 "),n("div",{staticClass:"animated fadeInDown reward-main"},[n("ul",{staticClass:"reward-all"},[n("li",{staticClass:"reward-item"},[n("img",{staticClass:"reward-img",attrs:{src:t.blogInfo.websiteConfig.weiXinQRCode}}),n("div",{staticClass:"reward-desc"},[t._v("微信")])]),n("li",{staticClass:"reward-item"},[n("img",{staticClass:"reward-img",attrs:{src:t.blogInfo.websiteConfig.alipayQRCode}}),n("div",{staticClass:"reward-desc"},[t._v("支付宝")])])])])]):t._e()]),n("div",{staticClass:"pagination-post"},[t.article.lastArticle.id?n("div",{class:t.isFull(t.article.lastArticle.id)},[n("router-link",{attrs:{to:"/articles/"+t.article.lastArticle.id}},[n("img",{staticClass:"post-cover",attrs:{src:t.article.lastArticle.articleCover}}),n("div",{staticClass:"post-info"},[n("div",{staticClass:"label"},[t._v("上一篇")]),n("div",{staticClass:"post-title"},[t._v(" "+t._s(t.article.lastArticle.articleTitle)+" ")])])])],1):t._e(),t.article.nextArticle.id?n("div",{class:t.isFull(t.article.nextArticle.id)},[n("router-link",{attrs:{to:"/articles/"+t.article.nextArticle.id}},[n("img",{staticClass:"post-cover",attrs:{src:t.article.nextArticle.articleCover}}),n("div",{staticClass:"post-info",staticStyle:{"text-align":"right"}},[n("div",{staticClass:"label"},[t._v("下一篇")]),n("div",{staticClass:"post-title"},[t._v(" "+t._s(t.article.nextArticle.articleTitle)+" ")])])])],1):t._e()]),t.article.recommendArticleList.length?n("div",{staticClass:"recommend-container"},[n("div",{staticClass:"recommend-title"},[n("v-icon",{attrs:{size:"20",color:"#4c4948"}},[t._v("mdi-thumb-up")]),t._v(" 相关推荐 ")],1),n("div",{staticClass:"recommend-list"},t._l(t.article.recommendArticleList,(function(e){return n("div",{key:e.id,staticClass:"recommend-item"},[n("router-link",{attrs:{to:"/articles/"+e.id}},[n("img",{staticClass:"recommend-cover",attrs:{src:e.articleCover}}),n("div",{staticClass:"recommend-info"},[n("div",{staticClass:"recommend-date"},[n("i",{staticClass:"iconfont iconrili"}),t._v(" "+t._s(t._f("date")(e.createTime))+" ")]),n("div",[t._v(t._s(e.articleTitle))])])])],1)})),0)]):t._e(),n("hr"),n("comment",{attrs:{type:t.commentType},on:{getCommentCount:t.getCommentCount}})],1)],1),n("v-col",{staticClass:"d-md-block d-none",attrs:{md:"3",cols:"12"}},[n("div",{staticStyle:{position:"sticky",top:"20px"}},[n("v-card",{staticClass:"right-container"},[n("div",{staticClass:"right-title"},[n("i",{staticClass:"iconfont iconhanbao",staticStyle:{"font-size":"16.8px"}}),n("span",{staticStyle:{"margin-left":"10px"}},[t._v("目录")])]),n("div",{attrs:{id:"toc"}})]),n("v-card",{staticClass:"right-container",staticStyle:{"margin-top":"20px"}},[n("div",{staticClass:"right-title"},[n("i",{staticClass:"iconfont icongengxinshijian",staticStyle:{"font-size":"16.8px"}}),n("span",{staticStyle:{"margin-left":"10px"}},[t._v("最新文章")])]),n("div",{staticClass:"article-list"},t._l(t.article.newestArticleList,(function(e){return n("div",{key:e.id,staticClass:"article-item"},[n("router-link",{staticClass:"content-cover",attrs:{to:"/articles/"+e.id}},[n("img",{attrs:{src:e.articleCover}})]),n("div",{staticClass:"content"},[n("div",{staticClass:"content-title"},[n("router-link",{attrs:{to:"/articles/"+e.id}},[t._v(" "+t._s(e.articleTitle)+" ")])],1),n("div",{staticClass:"content-time"},[t._v(t._s(t._f("date")(e.createTime)))])])],1)})),0)])],1)])],1)],1)},s=[],a=(n("99af"),n("c975"),n("d3b7"),n("ac1f"),n("25f0"),n("5319"),n("1276"),n("b311")),r=n.n(a),o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[t._m(0),n("div",{staticClass:"comment-input-wrapper"},[n("div",{staticStyle:{display:"flex"}},[n("v-avatar",{attrs:{size:"40"}},[this.$store.state.avatar?n("img",{attrs:{src:this.$store.state.avatar}}):n("img",{attrs:{src:this.$store.state.blogInfo.websiteConfig.touristAvatar}})]),n("div",{staticClass:"ml-3",staticStyle:{width:"100%"}},[n("div",{staticClass:"comment-input"},[n("textarea",{directives:[{name:"model",rawName:"v-model",value:t.commentContent,expression:"commentContent"}],staticClass:"comment-textarea",attrs:{placeholder:"留下点什么吧...","auto-grow":"",dense:""},domProps:{value:t.commentContent},on:{input:function(e){e.target.composing||(t.commentContent=e.target.value)}}})]),n("div",{staticClass:"emoji-container"},[n("span",{class:t.chooseEmoji?"emoji-btn-active":"emoji-btn",on:{click:function(e){t.chooseEmoji=!t.chooseEmoji}}},[n("i",{staticClass:"iconfont iconbiaoqing"})]),n("button",{staticClass:"upload-btn v-comment-btn",staticStyle:{"margin-left":"auto"},on:{click:t.insertComment}},[t._v(" 提交 ")])]),n("emoji",{attrs:{chooseEmoji:t.chooseEmoji},on:{addEmoji:t.addEmoji}})],1)],1)]),t.count>0&&t.reFresh?n("div",[n("div",{staticClass:"count"},[t._v(t._s(t.count)+" 评论")]),t._l(t.commentList,(function(e,i){return n("div",{key:e.id,staticClass:"pt-5",staticStyle:{display:"flex"}},[n("v-avatar",{staticClass:"comment-avatar",attrs:{size:"40"}},[n("img",{attrs:{src:e.avatar}})]),n("div",{staticClass:"comment-meta"},[n("div",{staticClass:"comment-user"},[e.webSite?n("a",{attrs:{href:e.webSite,target:"_blank"}},[t._v(" "+t._s(e.nickname)+" ")]):n("span",[t._v(t._s(e.nickname))]),1==e.userId?n("span",{staticClass:"blogger-tag"},[t._v("博主")]):t._e()]),n("div",{staticClass:"comment-info"},[n("span",{staticStyle:{"margin-right":"10px"}},[t._v(t._s(t.count-i)+"楼")]),n("span",{staticStyle:{"margin-right":"10px"}},[t._v(t._s(t._f("date")(e.createTime)))]),n("span",{class:t.isLike(e.id)+" iconfont icondianzan",on:{click:function(n){return t.like(e)}}}),n("span",{directives:[{name:"show",rawName:"v-show",value:e.likeCount>0,expression:"item.likeCount > 0"}]},[t._v(" "+t._s(e.likeCount))]),n("span",{staticClass:"reply-btn",on:{click:function(n){return t.replyComment(i,e)}}},[t._v(" 回复 ")])]),n("p",{staticClass:"comment-content",domProps:{innerHTML:t._s(e.commentContent)}}),t._l(e.replyDTOList,(function(s){return n("div",{key:s.id,staticStyle:{display:"flex"}},[n("v-avatar",{staticClass:"comment-avatar",attrs:{size:"36"}},[n("img",{attrs:{src:s.avatar}})]),n("div",{staticClass:"reply-meta"},[n("div",{staticClass:"comment-user"},[s.webSite?n("a",{attrs:{href:s.webSite,target:"_blank"}},[t._v(" "+t._s(s.nickname)+" ")]):n("span",[t._v(t._s(s.nickname))]),1==s.userId?n("span",{staticClass:"blogger-tag"},[t._v("博主")]):t._e()]),n("div",{staticClass:"comment-info"},[n("span",{staticStyle:{"margin-right":"10px"}},[t._v(" "+t._s(t._f("date")(s.createTime))+" ")]),n("span",{class:t.isLike(s.id)+" iconfont icondianzan",on:{click:function(e){return t.like(s)}}}),n("span",{directives:[{name:"show",rawName:"v-show",value:s.likeCount>0,expression:"reply.likeCount > 0"}]},[t._v(" "+t._s(s.likeCount))]),n("span",{staticClass:"reply-btn",on:{click:function(e){return t.replyComment(i,s)}}},[t._v(" 回复 ")])]),n("p",{staticClass:"comment-content"},[s.replyUserId!=e.userId?[s.replyWebSite?n("a",{staticClass:"comment-nickname ml-1",attrs:{href:s.replyWebSite,target:"_blank"}},[t._v(" @"+t._s(s.replyNickname)+" ")]):n("span",{staticClass:"ml-1"},[t._v(" @"+t._s(s.replyNickname)+" ")]),t._v(" ， ")]:t._e(),n("span",{domProps:{innerHTML:t._s(s.commentContent)}})],2)])],1)})),n("div",{directives:[{name:"show",rawName:"v-show",value:e.replyCount>3,expression:"item.replyCount > 3"}],ref:"check",refInFor:!0,staticClass:"mb-3",staticStyle:{"font-size":"0.75rem",color:"#6d757a"}},[t._v(" 共 "),n("b",[t._v(t._s(e.replyCount))]),t._v(" 条回复， "),n("span",{staticStyle:{color:"#00a1d6",cursor:"pointer"},on:{click:function(n){return t.checkReplies(i,e)}}},[t._v(" 点击查看 ")])]),n("div",{ref:"paging",refInFor:!0,staticClass:"mb-3",staticStyle:{"font-size":"0.75rem",color:"#222",display:"none"}},[n("span",{staticStyle:{"padding-right":"10px"}},[t._v(" 共"+t._s(Math.ceil(e.replyCount/5))+"页 ")]),n("paging",{ref:"page",refInFor:!0,attrs:{totalPage:Math.ceil(e.replyCount/5),index:i,commentId:e.id},on:{changeReplyCurrent:t.changeReplyCurrent}})],1),n("Reply",{ref:"reply",refInFor:!0,attrs:{type:t.type},on:{reloadReply:t.reloadReply}})],2)],1)})),n("div",{staticClass:"load-wrapper"},[t.count>t.commentList.length?n("v-btn",{attrs:{outlined:""},on:{click:t.listComments}},[t._v(" 加载更多... ")]):t._e()],1)],2):n("div",{staticStyle:{padding:"1.25rem","text-align":"center"}},[t._v(" 来发评论吧~ ")])])},c=[function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"comment-title"},[n("i",{staticClass:"iconfont iconpinglunzu"}),t._v("评论")])}],l=(n("4160"),n("a9e3"),n("498a"),n("159b"),n("2909")),u=n("2f3e"),m=n("bbe0"),d=n("bbe3"),p=n("f568"),f={components:{Reply:u["a"],Emoji:d["a"],Paging:m["a"]},props:{type:{type:Number}},created:function(){this.listComments()},data:function(){return{reFresh:!0,commentContent:"",chooseEmoji:!1,current:1,commentList:[],count:0}},methods:{replyComment:function(t,e){this.$refs.reply.forEach((function(t){t.$el.style.display="none"})),this.$refs.reply[t].commentContent="",this.$refs.reply[t].nickname=e.nickname,this.$refs.reply[t].replyUserId=e.userId,this.$refs.reply[t].parentId=this.commentList[t].id,this.$refs.reply[t].chooseEmoji=!1,this.$refs.reply[t].index=t,this.$refs.reply[t].$el.style.display="block"},addEmoji:function(t){this.commentContent+=t},checkReplies:function(t,e){var n=this;this.axios.get("/api/comments/"+e.id+"/replies",{params:{current:1,size:5}}).then((function(i){var s=i.data;n.$refs.check[t].style.display="none",e.replyDTOList=s.data,Math.ceil(e.replyCount/5)>1&&(n.$refs.paging[t].style.display="flex")}))},changeReplyCurrent:function(t,e,n){var i=this;this.axios.get("/api/comments/"+n+"/replies",{params:{current:t,size:5}}).then((function(t){var n=t.data;i.commentList[e].replyDTOList=n.data}))},listComments:function(){var t=this,e=this.$route.path,n=e.split("/"),i={current:this.current,type:this.type};switch(this.type){case 1:case 3:i.topicId=n[2];break;default:break}this.axios.get("/api/comments",{params:i}).then((function(e){var n,i=e.data;1==t.current?t.commentList=i.data.recordList:(n=t.commentList).push.apply(n,Object(l["a"])(i.data.recordList));t.current++,t.count=i.data.count,t.$emit("getCommentCount",t.count)}))},insertComment:function(){var t=this;if(!this.$store.state.userId)return this.$store.state.loginFlag=!0,!1;if(""==this.commentContent.trim())return this.$toast({type:"error",message:"评论不能为空"}),!1;var e=/\[.+?\]/g;this.commentContent=this.commentContent.replace(e,(function(t){return"<img src= '"+p["a"][t]+"' width='24'height='24' style='margin: 0 1px;vertical-align: text-bottom'/>"}));var n=this.$route.path,i=n.split("/"),s={commentContent:this.commentContent,type:this.type};switch(this.type){case 1:case 3:s.topicId=i[2];break;default:break}this.commentContent="",this.axios.post("/api/comments",s).then((function(e){var n=e.data;if(n.flag){t.current=1,t.listComments();var i=t.$store.state.blogInfo.websiteConfig.isCommentReview;i?t.$toast({type:"warnning",message:"评论成功，正在审核中"}):t.$toast({type:"success",message:"评论成功"})}else t.$toast({type:"error",message:n.message})}))},like:function(t){var e=this;if(!this.$store.state.userId)return this.$store.state.loginFlag=!0,!1;this.axios.post("/api/comments/"+t.id+"/like").then((function(n){var i=n.data;i.flag&&(-1!=e.$store.state.commentLikeSet.indexOf(t.id)?e.$set(t,"likeCount",t.likeCount-1):e.$set(t,"likeCount",t.likeCount+1),e.$store.commit("commentLike",t.id))}))},reloadReply:function(t){var e=this;this.axios.get("/api/comments/"+this.commentList[t].id+"/replies",{params:{current:this.$refs.page[t].current,size:5}}).then((function(n){var i=n.data;e.commentList[t].replyCount++,e.commentList[t].replyCount>5&&(e.$refs.paging[t].style.display="flex"),e.$refs.check[t].style.display="none",e.$refs.reply[t].$el.style.display="none",e.commentList[t].replyDTOList=i.data}))}},computed:{isLike:function(){return function(t){var e=this.$store.state.commentLikeSet;return-1!=e.indexOf(t)?"like-active":"like"}}},watch:{commentList:function(){var t=this;this.reFresh=!1,this.$nextTick((function(){t.reFresh=!0}))}}},h=f,v=(n("d69f"),n("2877")),C=n("6544"),g=n.n(C),y=n("8212"),_=n("8336"),b=Object(v["a"])(h,o,c,!1,null,"65e82974",null),k=b.exports;g()(b,{VAvatar:y["a"],VBtn:_["a"]});var x=n("7577"),w=n.n(x),S={components:{Comment:k},created:function(){this.getArticle()},destroyed:function(){this.clipboard.destroy(),w.a.destroy()},data:function(){return{config:{sites:["qzone","wechat","weibo","qq"]},imgList:[],article:{nextArticle:{id:0,articleCover:""},lastArticle:{id:0,articleCover:""},recommendArticleList:[],newestArticleList:[]},wordNum:"",readTime:"",commentType:1,articleHref:window.location.href,clipboard:null,commentCount:0}},methods:{getArticle:function(){var t=this,e=this;this.axios.get("/api"+this.$route.path).then((function(n){var i=n.data;document.title=i.data.articleTitle,t.markdownToHtml(i.data),t.$nextTick((function(){t.wordNum=t.deleteHTMLTag(t.article.articleContent).length,t.readTime=Math.round(t.wordNum/400)+"分钟",t.clipboard=new r.a(".copy-btn"),t.clipboard.on("success",(function(){t.$toast({type:"success",message:"复制成功"})}));var n=t.$refs.article.children;if(n.length)for(var i=0;i<n.length;i++){var s=n[i],a=/^H[1-4]{1}$/;a.exec(s.tagName)&&(s.id=i)}w.a.init({tocSelector:"#toc",contentSelector:".article-content",headingSelector:"h1, h2, h3",hasInnerContainers:!0,onClick:function(t){t.preventDefault()}});for(var o=t.$refs.article.getElementsByTagName("img"),c=0;c<o.length;c++)t.imgList.push(o[c].src),o[c].addEventListener("click",(function(t){e.previewImg(t.target.currentSrc)}))}))}))},like:function(){var t=this;if(!this.$store.state.userId)return this.$store.state.loginFlag=!0,!1;this.axios.post("/api/articles/"+this.article.id+"/like").then((function(e){var n=e.data;n.flag&&(-1!=t.$store.state.articleLikeSet.indexOf(t.article.id)?t.$set(t.article,"likeCount",t.article.likeCount-1):t.$set(t.article,"likeCount",t.article.likeCount+1),t.$store.commit("articleLike",t.article.id))}))},markdownToHtml:function(t){var e=n("d4cd"),i=n("1487"),s=new e({html:!0,linkify:!0,typographer:!0,breaks:!0,highlight:function(t,e){var n=(new Date).getTime();window.performance&&"function"===typeof window.performance.now&&(n+=performance.now());for(var s="xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g,(function(t){var e=(n+16*Math.random())%16|0;return n=Math.floor(n/16),("x"==t?e:3&e|8).toString(16)})),a='<button class="copy-btn iconfont iconfuzhi" type="button" data-clipboard-action="copy" data-clipboard-target="#copy'.concat(s,'"></button>'),r=t.split(/\n/).length-1,o='<span aria-hidden="true" class="line-numbers-rows">',c=0;c<r;c++)o+="<span></span>";if(o+="</span>",null==e&&(e="java"),e&&i.getLanguage(e)){var l=i.highlight(e,t,!0).value;return a+=l,r&&(a+='<b class="name">'+e+"</b>"),'<pre class="hljs"><code>'.concat(a,"</code>").concat(o,'</pre><textarea style="position: absolute;top: -9999px;left: -9999px;z-index: -9999;" id="copy').concat(s,'">').concat(t.replace(/<\/textarea>/g,"</textarea>"),"</textarea>")}}});t.articleContent=s.render(t.articleContent),this.article=t},previewImg:function(t){this.$imagePreview({images:this.imgList,index:this.imgList.indexOf(t)})},deleteHTMLTag:function(t){return t.replace(/<\/?[^>]*>/g,"").replace(/[|]*\n/,"").replace(/&npsp;/gi,"")},getCommentCount:function(t){this.commentCount=t}},computed:{blogInfo:function(){return this.$store.state.blogInfo},articleCover:function(){return"background: url("+this.article.articleCover+") center center / cover no-repeat"},isLike:function(){var t=this.$store.state.articleLikeSet;return-1!=t.indexOf(this.article.id)?"like-btn-active":"like-btn"},isFull:function(){return function(t){return t?"post full":"post"}}}},L=S,T=(n("609b"),n("65b9"),n("b0af")),$=n("62ad"),N=n("132d"),E=n("0fd9"),I=Object(v["a"])(L,i,s,!1,null,"24aebe1c",null);e["default"]=I.exports;g()(I,{VCard:T["a"],VCol:$["a"],VIcon:N["a"],VRow:E["a"]})}}]);