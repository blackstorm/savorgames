<template>
<div>
  <ul class="editor-toolbar">
    <li>
      <a href="javascript:;" @click="show('header')" data-balloon="标题" data-balloon-pos="top"><i class="fa fa-header fa-lg"></i></a>
      <div class="toolbar-menu toolbar-menu-header" v-show:="toolbarShow == 'header' ? true : false">
        <ul class="hdeader-list">
          <li><a href="javascript:;" @click="headerExecCommand('h3')"><h3>1号标题</h3></a></li>
          <li><a href="javascript:;" @click="headerExecCommand('H4')"><h4>2号标题</h4></a></li>
          <li><a href="javascript:;" @click="headerExecCommand('H5')"><h5>3号标题</h5></a></li>
          <li><a href="javascript:;" @click="headerExecCommand('H6')"><h6>4号标题</h6></a></li>
        </ul>
      </div>
    </li>
    <li>
      <!-- 文字颜色选择 -->
      <a href="javascript:;" @click="show('color')" data-balloon="字体颜色" data-balloon-pos="top">
        <i class="fa fa-leaf fa-lg"></i>
      </a>
      <div class="toolbar-menu toolbar-menu-color" v-show:="toolbarShow == 'color' ? true : false">
        <ul class="color-list">
          <li><a href="javascript:;" class="font-color font-color-1" @click.prevent="changeTextColor('#E33737')"></a></li>
          <li><a href="javascript:;" class="font-color font-color-2" @click.prevent="changeTextColor('#e28b41')"></a></li>
          <li><a href="javascript:;" class="font-color font-color-3" @click.prevent="changeTextColor('#c8a732')"></a></li>
          <li><a href="javascript:;" class="font-color font-color-4" @click.prevent="changeTextColor('#209361')"></a></li>
          <li><a href="javascript:;" class="font-color font-color-5" @click.prevent="changeTextColor('#418caf')"></a></li>
          <li><a href="javascript:;" class="font-color font-color-6" @click.prevent="changeTextColor('#aa8773')"></a></li>
          <li><a href="javascript:;" class="font-color font-color-7" @click.prevent="changeTextColor('#999999')"></a></li>
          <li><a href="javascript:;" class="font-color font-color-default" @click.prevent="changeTextColor('#333')" data-balloon="默认" data-balloon-pos="down"></a></li>
          <li><a href="javascript:;" data-balloon="自定义" data-balloon-pos="down" v-bind:style="color.input" v-show:="color.input.background ? true : false" class="font-color font-color-default" @click.prevent="changeTextColor(color.input.background)"></a></li>
        </ul>
        <input type="color" v-model:="color.input.background"/>
      </div>
    </li>
    <li><a href="javascript:;" @click="execCommandByKey('bold')" data-balloon="加粗" data-balloon-pos="top"><i class="fa fa-bold fa-lg"></i></a></li>
    <li><a href="javascript:;" @click="execCommandByKey('italic')" data-balloon="斜体" data-balloon-pos="top"><i class="fa fa-italic fa-lg"></i></a></li>
    <li><a href="javascript:;" @click="execCommandByKey('underline')" data-balloon="下划线" data-balloon-pos="top"><i class="fa fa-underline fa-lg"></i></a></li>
    <li>
      <a href="javascript:;" @click="execCommandByKey('InsertOrderedList')" data-balloon="有序列表" data-balloon-pos="top"><i class="fa fa-list-ol fa-lg" aria-hidden="true"></i></a>
    </li>
    <li><a href="javascript:;" @click="execCommandByKey('InsertUnorderedList')" data-balloon="无序列表" data-balloon-pos="top"><i class="fa fa-list-ul fa-lg" aria-hidden="true"></i></a></li>
    <li>
      <a href="javascript:;" @click="insertImage()" data-balloon="插入图片" data-balloon-pos="top"><i class="fa fa-file-image-o fa-lg" aria-hidden="true"></i></a>
      <div class="toolbar-menu toolbar-menu-image" v-show:="toolbarShow == 'image' ? true : false">
        <div class="faq">
          <p>暂时不支持图片上传：<a href="">FAQ</a></p>
          <p>您可以使用图床：<a href="https://sm.ms" target="_blank">sm.ms</a> <a href="https://www.niupic.com/" target="_blank">牛图</a></p>
        </div>
        <div class="input-group">
          <span class="input-group-addon addon-sm">https://</span>
          <input type="text" class="form-input input-sm" placeholder="image URL" v-model:="img.url">
          <a class="btn btn-sm input-group-btn" @click.prevent="insertImageOnsubmit()">插入</a>
        </div>
      </div>
    </li>
    <li><a href="javascript:;" @click="execCommandByKey('InsertHorizontalRule')" data-balloon="分割线" data-balloon-pos="top"><i class="fa fa-bookmark-o fa-lg" aria-hidden="true"></i></a></li>
    <li><a href="javascript:;" @click="execCommandByKey('removeFormat')" data-balloon="清除格式" data-balloon-pos="top"><i class="fa fa-eraser fa-lg"></i></a></li>
    <li><a href="javascript:;" @click="execCommandByKey('undo')" data-balloon="撤销" data-balloon-pos="top"><i class="fa fa-undo fa-lg"></i></a></li>
  </ul>
</div>
</template>
<script>
export default {
  data() {
    return {
      toolbarShow: null,
      color: {
        input: {
          background: null
        }
      },
      img: {
        url: null
      }
    };
  },
  methods: {
    show(value) {
      if (this.toolbarShow === value) {
        this.toolbarShow = null;
        return;
      }
      this.toolbarShow = value;
    },
    execCommandByKey(key) {
      document.execCommand(key, false, null);
    },
    changeTextColor(color) {
      document.execCommand('foreColor', false, color);
      this.show('color');
    },
    headerExecCommand(value) {
      document.execCommand('formatBlock', false, '<' + value + '>');
      this.show('header');
    },
    insertImage() {
      this.show('image');
    },
    insertImageOnsubmit() {
      var Expression = /https?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
      var objExp = new RegExp(Expression);
      if (objExp.test(this.img.url) !== true) {
        window.alert('您提交的URL必须是https链接，并符合URL规则，请确认拷贝无误。');
        return;
      }
      document.execCommand('insertImage', false, this.img.url);
      this.show('image');
    }
  }
};
</script>
<style>
.editor-toolbar{
  margin: 0;
  padding:0;
  list-style: none;
  border-top: 6px solid #333; 
  height: 46px;
  border-bottom: 1px solid rgba(153, 153, 153, 0.33);
  padding-left: 5px;
  padding-right: 5px;
}

.editor-toolbar > li {
  margin: 0;
  float: left;
  padding-top:8px;
  position: relative;
  display: inline-block;
}

.editor-toolbar > li > a {
  margin-left: 2px;
  margin-right: 2px;
  padding:6px 10px 6px 10px;
}

.editor-toolbar > li > a {
  color:#333;
}

.editor-toolbar > li > a:hover{
  border-radius: 4px;
  background-color: #16A085;
  cursor:pointer;
}

.editor-toolbar .toolbar-menu {
  position: absolute;
  top: 41px;
  left: 0;
  z-index: 21;
  background: #ffffff;
  text-align: left;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.3);
}

.editor-toolbar .toolbar-menu ul {
  list-style: none;
}

.toolbar-menu-color{
  width: 96px;
}

.color-list {
  height: 40px;
  margin: 10px 6px 6px 10px;
  padding: 0;
  min-width: 0;
}

.color-list li {
  float: left;
  margin: 0 4px 4px 0;
}
.color-list li .font-color {
    display: block;
    width: 16px;
    height: 16px;
    background: #dfdfdf;
    border-radius: 2px;
}
.toolbar-menu-color input{
/*  font-size: 13px;*/
  border:0;
  padding: 4px 9px 4px 9px;
  outline:none;
  width: 100%;
}

.color-list li .font-color:hover {
  cursor:pointer;
  opacity: 0.8;
}
.color-list li .font-color-1 {
  background: #E33737;
}
.color-list li .font-color-2 {
  background: #e28b41;
}
.color-list li .font-color-3 {
  background: #c8a732;
}
.color-list li .font-color-4 {
  background: #209361;
}
.color-list li .font-color-5 {
  background: #418caf;
}
.color-list li .font-color-6 {
  background: #aa8773;
}
.color-list li .font-color-7 {
  background: #999999;
}

.editor-toolbar .toolbar-menu-header {
  width: 150px;
}
.editor-toolbar .hdeader-list{
  margin: 0;
  padding: 0;
}
.editor-toolbar .hdeader-list>li{
  margin:0;
}
.editor-toolbar .hdeader-list li a{
  display: block;
  text-decoration: none;
}
.editor-toolbar .hdeader-list li a:hover{
  background: #a5a5a5;
}
.editor-toolbar .hdeader-list > li > a > h3, .editor-toolbar .hdeader-list > li > a > h4, .editor-toolbar .hdeader-list > li > a > h5,.editor-toolbar .hdeader-list > li > a > h6{
  margin:0;
  padding: 10px 10px 10px 10px;
  color: #333;
}
.editor-toolbar .toolbar-menu-image {
  width: 200px;
}
.editor-toolbar .toolbar-menu-image .faq {
  font-size: 12px;
  color:#888;
  padding: 8px;
  line-height: 1.4;
}
.editor-toolbar .toolbar-menu-image .faq p{
  margin:0;
}
.editor-toolbar .toolbar-menu-image .faq a {
  text-decoration: none;
}
</style>