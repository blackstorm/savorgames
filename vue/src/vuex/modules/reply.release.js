import {
  RELEASE_REPLY_BUTTON_SUCCESS,
  RELEASE_REPLY_BUTTON_FIL
} from '../types';

const state = {
  submitButton: false
};

const mutations = {
  [RELEASE_REPLY_BUTTON_SUCCESS](state) {
    state.submitButton = true;
  },
  [RELEASE_REPLY_BUTTON_FIL](state) {
    state.submitButton = false;
  }
};

export default {
  state,
  mutations
};
