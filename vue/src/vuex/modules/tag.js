import {
  GET_TAGS_SUCCESS
} from '../types';

const state = {
  tags: null
};

const mutations = {
  [GET_TAGS_SUCCESS](state, action) {
    state.tags = action.tags;
  }
};

export default {
  state,
  mutations
};
