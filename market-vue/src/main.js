import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from "axios";
import VueRouter from "vue-router";
import router from "@/router";
import moment from "moment";
import preview from 'vue-photo-preview'
import 'vue-photo-preview/dist/skin.css'
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'

let orignalSetItem = window.localStorage.setItem;   // 原生localStorage.setItem方法
localStorage.setItem = function(key,newValue){
  let setItemEvent = new Event("setItemEvent");  // 重写注册setItem
  setItemEvent.key = key;
  setItemEvent.newValue = newValue;
  window.dispatchEvent(setItemEvent);            // 派发setItem
  orignalSetItem.apply(this, arguments);         // 设置值
}

Vue.use(Viewer)
Viewer.setDefaults({
  // 需要配置的属性 注意属性并没有引号
  title: false,
  toolbar: false
})


Vue.use(preview)
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.prototype.$moment = moment;
Vue.prototype.$axios = axios
Vue.prototype.$http = 'http://localhost:8090'
Vue.prototype.$backUrl = 'http://localhost:8090/images/upload/'
Vue.use(VueRouter)
Vue.prototype.$moment = moment

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')