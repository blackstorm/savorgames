import {
  GET_RANK_TOPICS_SUCCESS,
  HOME_MENU_TOPIC_LOADING_FALSE,
  HOME_MENU_TOPIC_LOADING_TRUE
} from '../types';

const state = {
  datas: null,
  loading: false
};

const mutations = {
  [GET_RANK_TOPICS_SUCCESS](state, action) {
    state.datas = action.topics;
  },
  [HOME_MENU_TOPIC_LOADING_TRUE](state) {
    state.loading = true;
  },
  [HOME_MENU_TOPIC_LOADING_FALSE](state) {
    state.loading = false;
  }
};

export default {
  state,
  mutations
};
