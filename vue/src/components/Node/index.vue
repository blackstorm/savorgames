<template>
<div class="container nodes-content">
  <ul id="nodeList">
    <li v-for="node in nodes" class="animated" transition="bounce" stagger="30" v-show:="show">
      <div class="columns">
        <div class="column col-3">
          <img src="http://7xwe6g.com1.z0.glb.clouddn.com/unnamed.jpg" width="180" height="180"></div>
        <div class="column col-9">
          <h3>
            {{node.name}}
          </h3>
          <p>
          {{node.description}}
          </p>
          <ul class="nodeMemu">
            <li>
              <a v-link="'home'">{{getNodeLastTopicTitle(node.nid)}}</a>
            </li>
          </ul>
        </div>
      </div>
    </li>
  </ul>
</div>
</template>
<script>
import { getTypeLastTopicsAction, getNodesAction } from '../../vuex/actions';

export default {
  vuex: {
    getters: {
      lastTopics: ({Node}) => Node.last,
      nodes: ({Node}) => Node.nodes
    },
    actions: {
      getTypeLastTopicsAction,
      getNodesAction
    }
  },
  transitions: {
    bounce: {
      enterClass: 'zoomIn',
      leaveClass: 'zoomOut'
    }
  },
  methods: {
    getNodeLastTopicTitle(nid) {
      for (var i in this.lastTopics) {
        if (nid === this.lastTopics[i].nid) {
          return this.lastTopics[i].title;
        }
      }
    }
  },
  data() {
    return {
      show: false
    };
  },
  ready() {
    if (this.nodes === null) {
      this.getNodesAction();
    }
    if (this.lastTopics === null) {
      this.getTypeLastTopicsAction('node');
    }
    this.show = true;
  }
};
</script>
<style>
.nodes-content {
  width: 960px;
}
#nodeList ,.nodeMemu{
  margin: 0;
  padding: 0;
  list-style: none;
}

#nodeList {
  padding-top: 20px;
  padding-bottom: 20px;
}

#nodeList > li {
  width: 100%;
  /*padding: 24px;*/
  padding: 15px 25px;
  background-color: white;
  box-shadow: 0 0 2px rgba(0,0,0,0.12),0 1px 2px rgba(0,0,0,0.24);
  margin-bottom: 25px;
  overflow: hidden;
  border-radius: 4px;
  border-top: #EEE 1px solid;
}
.nodeMemu >li{
  float: left;
  width: auto;
  padding-right:10px;
}
.nodeMemu >li >a{
  text-decoration: none;
}
.nodeMemu >li > a:hover {
  cursor: pointer;
}
</style>