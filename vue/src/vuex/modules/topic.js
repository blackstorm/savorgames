import {
  GET_TOPIC_SUCCESS,
  SAVE_TOPIC_REPLY_SUCCESS,
  UP_TOPIC_REPLY_SUCCESS,
  UP_TOPIC_RANK
} from '../types';

const state = {
  topic: null,
  replys: null,
  tags: null
};

const mutations = {
  [GET_TOPIC_SUCCESS](state, action) {
    state.topic = action.topic;
    state.replys = action.replys;
    state.tags = action.tags;
  },
  [SAVE_TOPIC_REPLY_SUCCESS](state, action) {
    state.replys = action.replys;
    state.topic.topicReplys = state.topic.topicReplys + 1;
  },
  [UP_TOPIC_REPLY_SUCCESS](state, action) {
    var id = action.id;
    for (var i in state.replys) {
      if (state.replys[i].id === id) {
        state.replys[i].rank = state.replys[i].rank + 1;
      }
    }
  },
  [UP_TOPIC_RANK](state) {
    state.topic.topicRank = state.topic.topicRank + 1;
  }
};

export default {
  state,
  mutations
};
