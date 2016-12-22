import {
  GET_NODES_SUCCESS,
  GET_NODES_LAST_TOPICS_SUCCESS
} from '../types';

const state = {
  nodes: null,
  last: null
};

const mutations = {
  [GET_NODES_SUCCESS](state, action) {
    state.nodes = action.nodes;
  },
  [GET_NODES_LAST_TOPICS_SUCCESS](state, action) {
    state.last = action.res;
  }
};

export default {
  state,
  mutations
};
