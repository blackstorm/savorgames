<template>
<div class="post-stream-bar">
  <div class="post-stream-info">
    <ul>
      <li>回复 {{{topic.topicReplys}}}</li>
      <li>观看 {{{topic.topicLooks}}} 次</li>
      <li>Rank {{{topic.topicRank}}}</li>
    </ul>
  </div>
  <div class="post-stream-action">
    <ul>
      <li class="post-stream-action-icon">
        <a href="JavaScript:;" data-balloon="收藏" data-balloon-pos="top"> <i class="fa fa-heart" aria-hidden="true" @click.prevent="likeOnClick()"></i>
        </a>
        <a href="JavaScript:;" data-balloon="Rank + 1" data-balloon-pos="top"> <i class="fa fa-thumbs-o-up" aria-hidden="true" @click.prevent="rankOnClick()"></i>
      </a>
    </li>
  </ul>
</div>
</div>
</template>
<script>
import {topicRankUpAction} from '../../vuex/actions';
import swal from 'sweetalert'; // 更美观的alert
import {swalLoginContent} from '../../utils/swal.js';

export default {
  props: ['user', 'topic'],
  vuex: {
    actions: {
      topicRankUpAction
    }
  },
  methods: {
    likeOnClick() {
      console.log('收藏');
    },
    rankOnClick() {
      var fnc = function() {
        swal.close();
        this.$router.go({name: 'signin'});
      }.bind(this);
      if (this.user === null) {
        swal(swalLoginContent, fnc);
        return;
      }
      this.topicRankUpAction(this.topic.topicId);
    }
  }
};
</script>
<style>
.post-stream-bar {
  margin-left: 85px;
  margin-bottom: 25px;
  padding: 20px 0px;
}

.post-stream-action {
  display: inline;
}

.post-stream-action > ul {
  width: auto;
}

.post-stream-action > ul > li{
  display: inline;
  margin-right: 10px;
}
.post-stream-action-icon {
  color:#8FB299;
}
.post-stream-action-icon a {
  padding: 5px;
  margin-right: 10px;
  color:#8FB299;
  font-size: 16px;
}
.post-stream-action-icon a:hover {
  background-color: #F0F0F0;
  border-radius: 50%;
  color:#8FB299;
}
.post-stream-info {
  float: right;
}
.post-stream-info ul ,.post-stream-info li {
  margin:0;
  padding: 0;
}
.post-stream-info ul {
  list-style: none;
}
.post-stream-info ul >li {
  display: inline;
  padding-left: 10px;
  position: relative;
  margin-top: -5px;
}
.post-stream-info {
  color:#8FB299;
}
</style>