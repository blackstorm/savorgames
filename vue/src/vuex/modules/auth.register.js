import {
  GET_CAPTCHA_ID,
  REGISTER_BUTTON_FALSE,
  REGISTER_BUTTON_TRUE
} from '../types';

const state = {
  captcha: {
    id: null,
    url: null
  },
  submitButton: false
};

const mutations = {
  [GET_CAPTCHA_ID](state, id, url) {
    state.captcha.id = id;
    state.captcha.url = url;
  },
  [REGISTER_BUTTON_FALSE](state) {
    state.submitButton = false;
  },
  [REGISTER_BUTTON_TRUE](state) {
    state.submitButton = true;
  }
};

export default {
  state,
  mutations
};
