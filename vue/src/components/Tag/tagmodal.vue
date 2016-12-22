<template>
  <div class="tagmodal-plane plane">
    <a class="close" @click.prevent="modal.show = false" data-balloon="关闭" data-balloon-pos="left"> <i class="fa fa-times-circle"></i>
    </a>
    <div class="columns">
      <div class="column col-12 tag-icon">
        <img src="//7xwe6g.com1.z0.glb.clouddn.com/saozuh.jpg?imageView/1/w/100/h/100/q/80/format/png" />  
      </div>
    </div>
    <div class="columns">
      <div class="column col-12 tag-name">
        <h1>{{modal.tag.name}}</h1>
        <small data-balloon="标签下帖子" data-balloon-pos="right">×{{ modal.tag.topicNum }}</small>
      </div>
    </div>
    <div class="columns">
      <div class="column col-12 des">
        <header>Information</header>
        <span class="info">创建于：{{ modal.tag.create }} </span>
      </div>
    </div>
    <div class="columns">
      <div class="column col-12 des">
        <header>Description:</header>
        <p>{{ modal.tag.description }}</p>
      </div>
    </div>

  </div>
</template>
<script>
export default {
  props: ['modal']
};
</script>
<style>
.tagmodal-plane{
  width: 100%;
  background-color: #FFFBE0;
  border-radius: 4px;
  box-shadow:0 5px 10px rgba(0,0,0,.15);
  padding: 20px;
}
.tagmodal-plane .tag-icon{
  text-align: center;
  padding-top: 10px;
}
.tagmodal-plane .tag-icon img {
  border-radius: 50%;
}

.tagmodal-plane .tag-name {
 
  text-align: center;
}

.tagmodal-plane .column {
  padding: 0;
}

.tagmodal-plane .column header {
  color:#666666;
  font-size: 18px;
  padding-bottom: 8px;
}

.tagmodal-plane .column {
  color:#333;
}

.tagmodal-plane .tag-name > h1 {
  margin-top: 8px;
  margin-bottom: 0px;
}
.tagmodal-plane .des {
  padding-top: 15px;
  padding-bottom: 10px;
  padding-left: 20px;
  padding-right: 20px;
}
.tagmodal-plane .des p {
  line-height: 1.8;
}
.tagmodal-plane .close{
  position: absolute;
  right: 8px;
  top: 6px;
  font-size: 24px;
}
.tagmodal-plane .close:hover{
  cursor: pointer;
}
</style>