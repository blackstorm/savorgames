import {
  GET_GAMESTYLE_SUCCESS
} from '../types';

const state = {
  styles: null
};

const mutations = {
  [GET_GAMESTYLE_SUCCESS](state, action) {
    state.styles = action.styles;
  }
};

export default {
  state,
  mutations
};
