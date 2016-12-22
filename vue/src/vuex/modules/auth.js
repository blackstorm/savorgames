import {
  AUTH_LOGIN_SUCCESS,
  USERINFO_SUCCESS,
  USERINFO_FAILURE,
  LOGOUT_USER,
  AUTH_UPLOAD_AVATAR_SUCCESS
} from '../types';
import { getCookie } from '../../utils/authService';

const state = {
  token: getCookie('token') || null,
  user: null,
  status: null
};

const mutations = {
  [AUTH_LOGIN_SUCCESS](state, action) {
    state.token = action.token;
  },
  [USERINFO_SUCCESS](state, action) {
    state.user = action.user;
    state.status = action.status;
  },
  [USERINFO_FAILURE](state, action) {
    state.user = null;
  },
  [LOGOUT_USER](state, action) {
    state.token = getCookie('token') || null;
    state.user = null;
    state.token = null;
  },
  [AUTH_UPLOAD_AVATAR_SUCCESS](state, action) {
    state.user.avatar = action.avatar;
  }
};

export default {
  state,
  mutations
};
