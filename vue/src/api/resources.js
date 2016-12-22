import Vue from 'vue';
import VueResource from 'vue-resource';
import {API_ROOT} from '../config';
import { getCookie, hasToken } from '../utils/authService';
// import NProgress from 'nprogress'; 顶部进度条

Vue.use(VueResource);

Vue.http.options.crossOrigin = true;
Vue.http.options.credentials = true;

Vue.http.interceptors.push((request, next) => {
  // NProgress.start();
  // 这里对请求体进行处理
  request.headers = request.headers || {};
  if (hasToken()) {
    // window.alert('提交token ');
    request.headers['X-Auth-Token'] = getCookie('token');
  }
  next((response) => {
    // NProgress.done();
    // 这里可以对响应的结果进行处理
    if (response.status === 401) {
      // signOut();
      // window.location.pathname = '/signin';
    }
  });
});

export const AvatarUploadApi = API_ROOT + '/user/avatar/upload';
export const Qiniu_UploadUrl = 'http://up.qiniu.com'; // 或者https
export const Qiniu_Http_Domain = 'http://7xwe6g.com1.z0.glb.clouddn.com';

export const AuthResource = Vue.resource(API_ROOT + 'auth{/do}');
export const UserResource = Vue.resource(API_ROOT + 'user{/do}');
export const AvatarResource = Vue.resource(API_ROOT + 'user/avatar{/do}');

export const TagResource = Vue.resource(API_ROOT + 'tag{/do}');

export const TopicResource = Vue.resource(API_ROOT + 't{/do}');
export const TopicLastResource = Vue.resource(API_ROOT + 't/last?type={do}');
export const TopicTabResource = Vue.resource(API_ROOT + 't?tab={tab}');

export const NodeResource = Vue.resource(API_ROOT + 'n{/do}');

export const GamePlatformResource = Vue.resource(API_ROOT + '/game/platform{/do}');
export const GameStyleResource = Vue.resource(API_ROOT + '/game/style{/do}');

export const ReplyRankResource = Vue.resource(API_ROOT + '/r/rank/{do}');
export const TopicRankResource = Vue.resource(API_ROOT + '/t/rank/{do}');
export const AdminPostResource = Vue.resource(API_ROOT + 'admin/post?page={page}');
export const AdminPost = Vue.resource(API_ROOT + 'admin/post/{do}');
