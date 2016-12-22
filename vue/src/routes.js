import homeIndex from './components/Home/index.vue';
import AuthLogin from './components/Auth/login.vue';
import AuthSignup from './components/Auth/signup.vue';
import AuthSetting from './components/Auth/setting.vue';
import TopicRelease from './components/Topic/release/index.vue';
import Node from './components/Node/index.vue';
import Tags from './components/Tag/index.vue';
import Games from './components/Game/index.vue';
import GamesRelease from './components/Game/release/index.vue';
import Topic from './components/Topic/index.vue';
import Admin from './components/Admin/index.vue';

export default function(router) {
  router.map({
    '/': {
      name: 'home',
      component: homeIndex
    },
    '/signin': {
      name: 'signin',
      component: AuthLogin
    },
    '/signup': {
      name: 'signup',
      component: AuthSignup
    },
    '/setting': {
      name: 'setting',
      component: AuthSetting
    },
    '/topic/release': {
      name: 'topicrelease',
      component: TopicRelease
    },
    '/node': {
      name: 'node',
      component: Node
    },
    '/tags': {
      name: 'tags',
      component: Tags
    },
    '/games': {
      name: 'games',
      component: Games
    },
    '/game/release': {
      name: 'gamerelease',
      component: GamesRelease
    },
    '/topic/:id': {
      name: 'topic',
      component: Topic
    },
    'management': {
      name: 'management',
      component: Admin
    }
  });
}
