(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5272f513"],{"5ccf":function(t,a,s){"use strict";s.r(a);var e=function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",[s("div",{staticClass:"banner",style:t.cover},[s("h1",{staticClass:"banner-title"},[t._v("相册")])]),s("v-card",{staticClass:"blog-container"},[s("v-row",t._l(t.photoAlbumList,(function(a){return s("v-col",{key:a.id,attrs:{md:6}},[s("div",{staticClass:"album-item"},[s("v-img",{staticClass:"album-cover",attrs:{src:a.albumCover}}),s("router-link",{staticClass:"album-wrapper",attrs:{to:"/albums/"+a.id}},[s("div",{staticClass:"album-name"},[t._v(t._s(a.albumName))]),s("div",{staticClass:"album-desc"},[t._v(t._s(a.albumDesc))])])],1)])})),1)],1)],1)},o=[],n=(s("4160"),s("159b"),{created:function(){this.listPhotoAlbums()},data:function(){return{photoAlbumList:[]}},methods:{listPhotoAlbums:function(){var t=this;this.axios.get("/api/photos/albums").then((function(a){var s=a.data;t.photoAlbumList=s.data}))}},computed:{cover:function(){var t="";return this.$store.state.blogInfo.pageList.forEach((function(a){"album"==a.pageLabel&&(t=a.pageCover)})),"background: url("+t+") center center / cover no-repeat"}}}),i=n,c=(s("67a4"),s("2877")),r=s("6544"),l=s.n(r),u=s("b0af"),b=s("62ad"),d=s("adda"),m=s("0fd9"),v=Object(c["a"])(i,e,o,!1,null,"0c69ec64",null);a["default"]=v.exports;l()(v,{VCard:u["a"],VCol:b["a"],VImg:d["a"],VRow:m["a"]})},"67a4":function(t,a,s){"use strict";var e=s("80de"),o=s.n(e);o.a},"80de":function(t,a,s){}}]);