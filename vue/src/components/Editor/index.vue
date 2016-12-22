<template>
    <div class="editor-wrapper">
      <Toolbar></Toolbar>
      <div id="editor-content" v-el:editorcontent contenteditable="true" autofocus v-on:input="contentOnInput($event)">
        <p>Go</p>
      </div>
    </div>
</template>
<script>
import Toolbar from './toolbar';

export default {
  props: ['data'],
  data() {
    return {
      content: {
        focus: false,
        selection: null
      }
    };
  },
  components: {
    Toolbar
  },
  methods: {
    contentOnInput(event) {
      // var doc = event.target.innerHTML.replace(/<[\/]?(div)(:?\s+(:?class|style)=(['"])[^'"]*['"])*>/g, function(m, m1) {return m.replace('div', 'p');});
      this.data = event.target.innerHTML;
    }
  }
};
</script>
<style>
.editor-wrapper{
  min-height: 460px;
  min-width: 100%;
  border-radius: 4px;
  border:1px solid #dad1d1;
}
.editor-wrapper #editor-content{
  height:100%;
  width: 100%;
  border:0;
  padding: 22px 15px 40px;
  min-height: 300px;
  font-size: 15px;
  font-family: arial, sans-serif;
  line-height: 1.6;
  color: #333;
  outline: none;
  word-wrap: break-word;
}
</style>