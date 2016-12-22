<template>
  <section class="menu-topics">
    <Stripeloading v-show:="loading" transition="fade" transition-mode="out-in"></Stripeloading>
    <div class="columns item" v-for="t in data" @click.prevent="goPost(t.topicId)">
      <div class="column col-1">
        <img :src="'//' + t.memberAvatar" height="50" class="topic-avatar"></div>
      <div class="column col-8">
        <a v-link="{ name: 'topic', params: { id: t.topicId }}" class="topic-link">{{t.topicTitle}}</a>
      </div>
      <div class="column col-3 icon">
        <span class="reply">
          <span class="icon-tips">回复</span> <i class="fa fa-hand-paper-o" aria-hidden="true"></i>
        </span>
        <span class="favrite">
          <span class="icon-tips">like</span> <i class="fa fa-heart-o" aria-hidden="true"></i>
        </span>
        <span class="share">
          <span class="icon-tips">分享</span>
          <i class="fa fa-send-o" aria-hidden="true"></i>
        </span>
      </div>
    </div>
  </section>
</template>
<script>
import Stripeloading from '../../loading/stripeloading.vue';

export default {
  components: {
    Stripeloading
  },
  methods: {
    goPost(tid) {
      this.$router.go({name: 'topic', params: {id: tid}});
    }
  },
  props: ['data'],
  vuex: {
    getters: {
      loading: ({HomeTopic}) => HomeTopic.loading
    }
  }
};
</script>
<style>
.menu-topics{
  padding-right: 1rem;
  padding-left: 20px;
  padding-top: 10px;
}
.topic-avatar {
  border-radius: 4px;
}
.topic-link {
  font-size: 15px;
  color:#47525d;
}
.menu-topics .item:hover{
  background-color: #f8f8f8;
}
.menu-topics .item .icon > span {
  display: block;
  width: 33.33333333%;
  float: left;
  text-align: right;
  color:#888;
}

.menu-topics .item .icon {
  cursor: pointer;
}


.menu-topics .item .icon > span .icon-tips {
  color:#888;
  display: none;
  font-size: 12px;
}
.menu-topics .item:hover .icon > span .icon-tips {
  display: inline;
}

.menu-topics .item:hover .reply > i{
  color:#f3b61e;
}
.menu-topics .item:hover .favrite > i{
  color:red;
}
.menu-topics .item:hover .share > i{
  color: blue;
}
</style>