<template>
  <form class="form-game">
    <div class="tips">
      <ol>
        <li>游戏平台</li>
        <li>游戏类型</li>
      </ol>
    </div>
    <div class="form-group platforms">
      <h6>选择游戏平台</h6>
      <span v-for="p in platformsStore" 
          @click.prevent="platformOnclick(p.platformId, $event)" v-bind:class="{'platform-active': platformIsActive(p.platformId)}">{{p.platformName}}</span>
    </div>
    <div class="form-group gamestyle">
      <h6>游戏类型</h6>
      <select v-model="form.style">
        <option v-for="s in stylesStore" :value="s.id">{{s.code}}+{{s.codeName}}</option>
      </select>
    </div>
    <div class="form-group">
      <div class="btn-group btn-group-block">
        <button class="btn btn-primary" @click.prevent="show = 'info'">上一步</button>
        <button class="btn btn-primary"  @click.prevent="enter()">下一步</button>
      </div>
    </div>
  </form>
</template>
<script>
import {getGamePlatformsAction, getGameStylesAction} from '../../../vuex/actions';

export default {
  props: ['form', 'show'],
  vuex: {
    getters: {
      platformsStore: ({GamePlatforms}) => GamePlatforms.platforms,
      stylesStore: ({GameStyle}) => GameStyle.styles
    },
    actions: {
      getGamePlatformsAction,
      getGameStylesAction
    }
  },
  methods: {
    platformIsActive(id) {
      for (var i in this.form.platforms) {
        if (this.form.platforms[i] === id) {
          return true;
        }
      }
      return false;
    },
    platformOnclick(id, e) {
      var className = 'platform-active';
      for (var i in e.srcElement.classList) {
        if (e.srcElement.classList[i] === className) {
          e.srcElement.classList.remove(className);
          this.removePlatform(id);
          return;
        }
      }
      e.srcElement.classList.add(className);
      this.addPlatform(id);
    },
    addPlatform(id) {
      this.form.platforms.push(id);
    },
    removePlatform(id) {
      this.form.platforms.$remove(id);
    },
    back() {
      this.show = 'info';
    },
    enter() {
      if (this.form.platforms.length === 0) {
        window.alert('请选择游戏平台');
        return;
      }
      if (this.form.style === 0) {
        window.alert('请选择游戏类型');
        return;
      }
      window.alert('ok');
    }
  },
  created() {
    if (this.platformsStore === null) {
      this.getGamePlatformsAction();
    }
    if (this.stylesStore === null) {
      this.getGameStylesAction();
    }
  }
};
</script>
<style>

.form-game .platforms,.gamestyle {
  text-align: center;
  padding: 20px;
}

.form-game .platforms span {
  padding: 10px;
  margin: 10px;
}

.form-game .platforms span:hover{
  cursor: pointer;
}

.form-game h6 {
  margin:0;
  padding: 20px;
  color:#666;
}

.form-game .platforms span:hover,.platform-active {
  background-color: #333;
  color:#fff;
}
</style>