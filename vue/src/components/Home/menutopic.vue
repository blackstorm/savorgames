<template>
  <div id="home-topic-box" class="container">
    <div class="columns">
      <div class="column col-3">
        <ul class="menu-topic">
          <li>
            <a href="javascript:;" @click.prevent="getTopicByKey('rank')" 
            v-bind:class="{'active': active == 'rank' ? true : false}">Rank</a>
          </li>
          <li>
            <a href="javascript:;" @click.prevent="getTopicByKey('latest')" 
            v-bind:class="{'active': active == 'latest' ? true : false}">刚刚发布</a>
          </li>
          <li>
            <a href="javascript:;" @click.prevent="getTopicByKey('key')">分享与推荐</a>
          </li>
          <li>
            <a href="javascript:;" @click.prevent="getTopicByKey('key')">问与答</a>
          </li>
          <li>
            <a href="javascript:;" @click.prevent="getTopicByKey('key')">新人有爱</a>
          </li>
        </ul>
      </div>
      <div class="column col-9">
        <Topicbox :data="topics"></Topicbox>
      </div>
    </div>
  </div>
</template>
<script>
import Topicbox from './menutopic/topic.vue';
import {getTabTopicsAction} from '../../vuex/actions';

export default {
  data() {
    return {
      active: 'rank'
    };
  },
  vuex: {
    getters: {
      topics: ({HomeTopic}) => HomeTopic.datas
    },
    actions: {
      getTabTopicsAction
    }
  },
  props: ['data'],
  components: {
    Topicbox
  },
  methods: {
    getTopicByKey(key) {
      if (this.active === key) return;
      this.active = key;
      this.getTabTopicsAction(key);
    }
  },
  created() {
    if (this.topics === null) {
      this.getTabTopicsAction('rank');
    }
  }
};
</script>
<style>
#home-topic-box {
  width: 1200px;
}
.menu-topic {
  list-style: none;
  height: auto;
  display:inline-block;
  margin:0;
  min-width: 100%;
}
.menu-topic > li {
  margin-top: 0;
  display: block;
  box-sizing: border-box;
  float: left;
  text-align: center;
  width: 100%;
}

.menu-topic> li .active {
  background-color: #1abc9c;
  color:#333;
}

.menu-topic> li > a{
  display: block;
  min-width: 100%;
  padding: 30px 10px;
  background-color: #d4fbf3;
}
.menu-topic> li > a:hover{
  background-color: #1abc9c;
}
</style>