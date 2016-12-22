import Vue from 'vue';
import App from './components/app.vue';
import VueRouter from 'vue-router';
import VueValidator from 'vue-validator';
import configRouter from './routes';
import { sync } from 'vuex-router-sync'; // 貌似是异步之类的裤
import store from './vuex/store';
import filters from './utils/filters';
// import './assets/style/spectre.css';
import './assets/style/sg.css';
import './assets/style/animate.css';
import './assets/style/cropper.min.css';

Vue.use(VueRouter); // 使用路由
Vue.use(VueValidator);

Object.keys(filters).forEach(k => Vue.filter(k, filters[k]));

const router = new VueRouter({
  history: true,
  saveScrollPosition: true,
  suppressTransitionError: true
});

configRouter(router);

sync(store, router);

router.start(Vue.extend(App), '#root');

window.router = router;
