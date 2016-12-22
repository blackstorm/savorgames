import {
  GET_ADMIN_POSTS_SUCCESS,
  ADMIN_DEL_TOPIC_SUCCESS
} from '../types';

const state = {
  posts: null
};

const mutations = {
  [GET_ADMIN_POSTS_SUCCESS](state, action) {
    state.posts = action.posts;
  },
  [ADMIN_DEL_TOPIC_SUCCESS](state, action) {
    for (var i in state.posts) {
      if (state.posts[i].tid === action.tid) {
        state.posts[i].status = 1;
      }
    }
  }
};

export default {
  state,
  mutations
};
