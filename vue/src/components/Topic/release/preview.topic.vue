<template>
  <div class="preview-plane plane">
    <div class="columns">
      <div class="column col-12">
        <h4>{{data.node.name}}</h4>
        <span v-for="tag in data.tags">
          {{tag.name}}
        </span>
        <div class="title">
          <h1>{{data.title}}</h1>
        </div>
        <div>{{{data.content}}}</div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: ['data']
};
</script>
<style>
.preview-plane {
  width: 100%;
  max-height: 1140px;
  overflow-x: hidden;
  overflow-y: auto;
  background-color: #FFFFCC;
  border-radius: 4px;
  box-shadow:0 5px 10px rgba(0,0,0,.15);
  padding:22px 28px 22px 28px;
}
</style>