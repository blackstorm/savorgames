<template>
<div class="form-group">
  <div class="form-autocomplete">

    <div class="form-autocomplete-input">

      <div class="chip-sm" v-for="tag in selectTags">
        <img src="http://picturepan2.github.io/spectre/demo/img/avatar-4.png" class="avatar">
        <span class="chip-name">
          {{tag.name}}
        </span>
        <button class="btn btn-clear" @click.prevent="removeSelectTag(tag)"></button>  
      </div>

      <input class="form-input" type="text" placeholder="选择+搜索标签" @focus.prevent="inputOnFocus()" v-model="query" />
      <button class="btn btn-sm btn-block" v-show:="tagListShow" @click.prevent="hiddenTagList()" style="margin-bottom:7px; border:none;">关闭列表</button>
    </div>
    <ul id="release-tags" class="form-autocomplete-list" v-show:="tagListShow" style="z-index:900;">
      <li class="form-autocomplete-item" v-for="tag in temp | orderBy 'name' | filterBy query in 'name' 'description'" @click.prevent="ListTagOnClick(tag)" track-by="id">
        <div class="chip hand">
          <div class="chip-icon">
            <img src="http://picturepan2.github.io/spectre/demo/img/avatar-4.png" class="avatar"></div>
          <div class="chip-content">
            {{tag.name}}
            <small data-balloon="标签下共有{{tag.topicNum}}个主题" data-balloon-pos="right">{{tag.topicNum}}</small>
          </div>
        </div>
      </li>
    </ul>
  </div>
</div>
</template>
<script>
import {
  getTagsActions
} from '../../../vuex/actions';

export default {
  props: ['selectTags'],
  vuex: {
    getters: {
      tagsStore: ({Tag}) => Tag.tags
    },
    actions: {
      getTagsActions
    }
  },
  data() {
    return {
      query: null,
      tagListShow: false,
      // selectTags: []
      temp: []
    };
  },
  methods: {
    // 隐藏标签列表
    hiddenTagList() {
      this.tagListShow = false;
    },
    // 移除选中的标签
    removeSelectTag(tag) {
      this.selectTags.$remove(tag);
      this.temp.push(tag);
    },
    // 标签点击事件
    ListTagOnClick(tag) {
      if (this.selectTags.length === 5) {
        window.alert('您最多只能选择5个标签');
        this.hiddenTagList();
        return;
      }
      for (var i in this.selectTags) {
        if (this.selectTags[i].id === tag.id) {
          window.alert('你已经选择过此标签了');
          return;
        }
      }
      this.selectTags.push(tag);
      this.temp.$remove(tag);
    },
    // input得到焦点
    inputOnFocus() {
      this.tagListShow = true;
      // 初始化一个temp  数组为了自由修改DOM
      if (this.temp.length === 0) {
        for (var i in this.tagsStore) {
          this.temp.push(this.tagsStore[i]);
        }
      }
    }
  },
  created() {
    if (this.tagsStore === null || typeof this.tagsStore === 'undefined') {
      this.getTagsActions();
    }
  }
};
</script>
<style>
#release-tags {
  max-height: 300px;
  overflow-x: hidden;
  overflow-y: auto;
}
</style>