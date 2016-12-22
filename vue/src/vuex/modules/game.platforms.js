import {
  GET_PLATFORM_SUCCESS
} from '../types';

const state = {
  platforms: null
};

const mutations = {
  [GET_PLATFORM_SUCCESS](state, action) {
    state.platforms = action.platforms;
  }
};

export default {
  state,
  mutations
};
