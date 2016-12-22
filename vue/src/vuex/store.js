import Vue from 'vue';
import Vuex from 'vuex';
import middlewares from './middlewares';
import Register from './modules/auth.register';
import showmsg from './modules/showmsg';
import Auth from './modules/auth';
import Node from './modules/node';
import Tag from './modules/tag';
import GamePlatforms from './modules/game.platforms';
import TopicRelease from './modules/topic.release';
import ReplyRelease from './modules/reply.release';
import Topic from './modules/topic';
import Admin from './modules/admin';
import HomeTopic from './modules/topic.home';
import GameStyle from './modules/game.style';
import { dev } from '../config';

// const debug = true; // 生产模式下请设置为false
Vue.use(Vuex);
Vue.config.debug = dev;
Vue.config.warnExpressionErrors = false;

export default new Vuex.Store({
  modules: {
    Register,
    showmsg,
    Auth,
    Node,
    Tag,
    GamePlatforms,
    GameStyle,
    TopicRelease,
    HomeTopic,
    Topic,
    ReplyRelease,
    Admin
  },
  strict: dev,
  middlewares
});
