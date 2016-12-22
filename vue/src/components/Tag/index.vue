<template>
  <div id="tags" class="container index-content"> 
    <div class="tag-plane">
      <div class="columns">
        <div class="column col-12">
          <div class="input-group">
            <input type="text" class="form-input" v-model="query" placeholder="输入关键字" />  
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column col-12 tagcloud">
          <ul>
            <li v-for="tag in tagsStore | filterBy query | orderBy 'name'" track-by="id" @click.prevent="tagOnClick(tag)">
              <a href="">
                {{tag.name}}
                <small>×{{tag.topicNum}}</small>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <div id="tag-md" class="pb2md fade-in-scale" v-bind:class="{'pb2md-show': modal.show}">
      <tagmodal :modal.sync="modal"></tagmodal>
    </div>
    <div class="pb2md-overlay" v-on:click="modal.show = false"></div>
  </div>
</template>
<script>
import {
  getTagsActions
} from '../../vuex/actions';
import Tagmodal from './tagmodal.vue';
import ModelUtils from '../../utils/model';

export default {
  components: {
    Tagmodal
  },
  data() {
    return {
      query: null,
      modal: {
        show: false,
        tag: null
      }
    };
  },
  vuex: {
    getters: {
      tagsStore: ({Tag}) => Tag.tags
    },
    actions: {
      getTagsActions
    }
  },
  watch: {
    'modal.show': {
      handler: (val, oldVal) => {
        if (val === false) {
          ModelUtils.hidden(document.getElementById('tag-md'));
        }
      },
      deep: true
    }
  },
  methods: {
    tagOnClick(tag) {
      ModelUtils.show(document.getElementById('tag-md'));
      this.modal.show = true;
      this.modal.tag = tag;
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
#tags .pb2md-overlay {
  background:none;
}
.tag-plane{
  padding-bottom: 20px;
  padding-top: 20px;
}
.tag-plane a{
  text-decoration: none;
}
.tagcloud > ul{
  margin:0;
  padding: 0;
  list-style: none;
}
.tagcloud > ul > li {
  float: left;
  font-size: 14px;
  padding-bottom: 20px;
  padding-right: 14px;
}
.tagcloud > ul > li > a {
  padding: 5px 8px;
  background-color:#393E46;
  color: #fff;
  border-radius: 2px;
}
.tagcloud > ul > li > a:hover{
  cursor: pointer;
  color:#333;
  background-color:#FCDA05;
}
#tag-md {
  width: 497px;
  height:auto;
}
</style>