<template>
<div class="admin-plane">
  <div class="admin-plane-nav">
    <ul>
      <li class="back">
        <a href="JavaScript:;" @click.prevent="backOnClick()"><h1>控制台</h1></a>
      </li>
      <li>
        <h1>Posts</h1>
      </li>
    </ul>
  </div>
  <div id="post-table" class="content">
    <table class="table table-striped table-hover" style="text-align: center;">
      <thead>
        <tr>
          <th>ID</th>
          <th>用户</th>
          <th>标题</th>
          <th>板块</th>
          <th>发布日期</th>
          <th>状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="p in posts" v-bind:class="{ 'thdel': p.status == 1}">
          <td>{{p.tid}}</td>
          <td>{{p.nickname}}</td>
          <td class="title">{{p.title}}</td>
          <td class="node">
            {{p.n_name}}
          </td>
          <td>{{p.created}}</td>
          <td>{{p.status}}</td>
          <td class="actions">
            <ul>
              <li><a href="JavaScript:;" @click.prevent="delOnClick(p.tid, p.title)"><i class="fa fa-trash-o"></i></a></li>
            </ul>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</div>
</template>
<script>
import {getAdminPostsActions, getNodesAction, adminDelTopicActions} from '../../../vuex/actions';
import swal from 'sweetalert'; // 更美观的alert

export default {
  vuex: {
    actions: {
      getAdminPostsActions,
      getNodesAction,
      adminDelTopicActions
    },
    getters: {
      posts: ({Admin}) => Admin.posts,
      nodes: ({Node}) => Node.nodes
    }
  },
  methods: {
    backOnClick() {
      this.tab = null;
    },
    delOnClick(tid, title) {
      var fnc = function() {
        swal.close();
        this.adminDelTopicActions(tid);
      }.bind(this);
      swal({title: '确定删除？',
        text: '准备删除' + title + '这篇帖子',
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#DD6B55',
        confirmButtonText: '删除！',
        closeOnConfirm: false
      }, fnc);
    }
  },
  props: ['tab'],
  created() {
    if (this.nodes === null) {
      this.getNodesAction();
    }
    this.getAdminPostsActions(1);
  }
};
</script>
<style>
.admin-plane {
  width: 100%;
  margin-top: -50px;
}
.admin-plane .admin-plane-nav ul,.admin-plane .admin-plane-nav li{

}
.admin-plane .admin-plane-nav li {
  display: inline-block;
}
.admin-plane .admin-plane-nav {
  height:120px;
  width: 100%;
  background-color: #F8F8F8;
}
.admin-plane-nav ul {
  width: 1400px;
  margin:auto;
}
.admin-plane .content, .admin-plane-nav ul {
  padding-left: 1rem;
  padding-right: 1rem;
}
.admin-plane .content {
  width: 1400px;
  margin:auto;
}
.admin-plane .content td ul {
  margin:0;
}
.admin-plane .content td.actions li{
  display: inline-block;
  margin:0;
  margin-right: 5px;
}
.admin-plane .content td.actions a {
  padding: 4px 8px;
  font-size: 16px;
  background-color: #f2f2f2;
  border-radius: 4px;
}
#post-table .thdel {
  background-color: #CC0000;
}
.admin-plane .content td.actions a:hover{
  background-color: #333;
  color: #fff;
}
</style>