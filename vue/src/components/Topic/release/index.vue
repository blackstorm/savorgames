<template>
  <div id="topic-release" class="container">

    <div class="columns">
      <div class="column col-9">
        <div class="form-group">
          <input class="form-input titleInput" type="text" placeholder="标题" v-model="from.title" maxlength="80" minlength="5" />  
        </div>
        <div class="form-group">
          <Editor :data.sync="from.content"></Editor>
        </div>
      </div>
      <div class="column col-3">
        <select-node :select.sync="from.node"></select-node>
        <p style="text-align: center;">哦，快挑选几个标签，准备发射了！</p>
        <select-tag :select-tags.sync="from.tags"></select-tag>
        <div class="form-group">
          <div class="btn-group btn-group-block">
            <button class="btn btn-primary" @click.prevent="previewButtonOnclick()">预览</button>
            <button class="btn btn-primary" @click.prevent="submit()" :class="{ loading: submitButtonState }">发布</button>
          </div>
        </div>
        <div class="form-group">
          <label class="form-switch">
            <input type="checkbox" v-model="from.remindMe"> <i class="form-icon"></i>
            当有回复时，提醒我
          </label>
        </div>
        <div class="form-group" v-show:="showError">
          <div class="toast toast-danger">
            <button class="btn btn-clear float-right" @click.prevent="hiddenErrorMessage()"></button>
            <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
            {{errorMessage}}
          </div>
        </div>
        <div class="form-group">
          <p>FAQ</p>
          <ol>
            <li>
              <a href="">为什么只支持HTTPS的图片链接？</a>
            </li>
            <li>
              <a href="">什么样的帖子会被降权？</a>
            </li>
          </ol>
          <p>小贴士</p>
          <ol>
            <li>
              <a href="">上传图片到HTTPS图床</a>
            </li>
            <li>
              <a href="">简单格式写好一篇轻松工整的帖子</a>
            </li>
          </ol>
        </div>
      </div>
    </div>

    <div id="topic-md" class="pb2md fade-in-scale" v-bind:class="{'pb2md-show': model.show}">
      <preview-model :data.sync="from"></preview-model>
    </div>
    <div class="pb2md-overlay" @click.prevent="hiddenPreview()"></div>
  </div>
</template>
<script>
import Xss from 'xss';
import Editor from '../../Editor/index.vue';
import SelectNode from './select.node.vue';
import SelectTag from './select.tag.vue';
import PreviewModel from './preview.topic.vue';
import ModelUtils from '../../../utils/model';
import {releaseTopicActions} from '../../../vuex/actions';

export default {
  vuex: {
    getters: {
      userStore: ({Auth}) => Auth.user,
      submitButtonState: ({TopicRelease}) => TopicRelease.submitButton
    },
    actions: {
      releaseTopicActions
    }
  },
  data() {
    return {
      showError: false,
      errorMessage: '',
      from: {
        title: null,
        content: '<p></p>',
        node: null,
        tags: [],
        remindMe: false
      },
      model: {
        show: false
      }
    };
  },
  components: {
    Editor,
    SelectNode,
    SelectTag,
    PreviewModel
  },
  methods: {
    previewButtonOnclick() {
      this.model.show = true;
      ModelUtils.show(document.getElementById('topic-md'));
    },
    hiddenPreview() {
      ModelUtils.hidden(document.getElementById('topic-md'));
      this.model.show = false;
    },
    submit() {
      if (this.from.title === null || this.from.title === '') {
        this.errorMessage = '请填写标题';
        this.showError = true;
        return;
      }

      if (this.from.content === '' || this.from.content === '<p></p>' || this.from.content === null) {
        this.errorMessage = '正文还没有填写';
        this.showError = true;
        return;
      }

      if (this.from.node === null) {
        this.errorMessage = '您必须选择一个节点';
        this.showError = true;
        return;
      }

      if (this.from.tags.length > 5) {
        this.errorMessage = '您最多只能选择5个标签';
        this.showError = true;
        return;
      }
      // 封装数据
      var tagsid = [];
      for (var i in this.from.tags) {
        tagsid.push(this.from.tags[i].id);
      }
      var topic = {
        title: this.from.title,
        content: Xss(this.from.content), // 防止XSS攻击
        nid: this.from.node.nid,
        tags: tagsid,
        remindMe: this.from.remindMe
      };
      this.releaseTopicActions(topic);
    },
    hiddenErrorMessage() {
      this.showError = false;
    }
  }
};
</script>
<style>
#topic-release{
  width: 1200px;
  margin-top: 60px;
}

#topic-release .form-group{
  margin-bottom:1.8rem;
}

#topic-release .selectBox {
  position: absolute;
  border: 1px solid #d2cccc;
  top:45px;
  width: 276px;
  background:#ffffff;
  padding: 8px;
  border-radius: 4px;
  -webkit-box-shadow: 0 4px 5px rgba(0, 0, 0, .15);
  -moz-box-shadow: 0 4px 5px rgba(0, 0, 0, .15);
  -o-box-shadow: 0 4px 5px rgba(0, 0, 0, .15);
  box-shadow: 0 4px 5px rgba(0, 0, 0, .15);
  overflow:hidden;
}
#topic-release .selectBox .results {
  list-style: none;
  margin:0;
  margin-top: 10px;
  max-height: 300px;
  overflow-x: hidden;
  overflow-y: auto;
}
#topic-release .selectBox .results li {
  margin:0; 
  padding: 8px;
  padding:16px 8px 16px 8px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

#topic-release .selectBox .results li small{
  color:#999999;
}

#topic-release .selectBox .results li:hover{
  background-color: #1abc9c;
  color:#fff;
  cursor: pointer;
}
#topic-release .selectBox .results li:hover small {
  padding-left: 6px;
  transition: padding .3s;
  -moz-transition: padding .3s;  /* Firefox 4 */
  -webkit-transition: padding .3s; /* Safari 和 Chrome */
  -o-transition: padding .3s;  /* Opera */
}
.selectBox .message{
  color:#999;
  text-align: center;
  font-size: 13px;
}
.selectBox .message a{
  text-decoration: none;
}

#topic-md {
  width: 1200px;
  height:auto;
}
</style>