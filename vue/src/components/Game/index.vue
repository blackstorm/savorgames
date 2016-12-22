<template>
<div style="background-color:#f2f2f2;">
  <div class="game-banner">
    <div class="backdrop"></div>
    <div class="content">
      <div class="columns">
        <div class="column col-12">
          <h1>
            <a href="">文明5</a>
          </h1>
          <p>
            游戏为Steam平台独占。游戏新加入远距轰炸能力，玩家可于战线后方发射武器，发展战场致胜的新策略。除了首次在《文明5》登场新游戏特色外，游戏也将强调多样化的社群、模组和多人游戏元素。
          </p>
        </div>
      </div>
    </div>
  </div>
  <div class="container games">

    <section class="game-items clearfix">
      <div class="game-item" v-for="i in 20">
        <div class="modal-thumbnail">
          <div class="modal-thumbnail-gameimage" style="background-image:url('http://7xwe6g.com1.z0.glb.clouddn.com/nomo.jpg?imageView2/1/h/315/w/260/q/80/format/png')"></div>
          <div class="modal-thumbnail-overlay"></div>
          <div class="modal-thumbnail-information">
            <div class="information-left"> <i class="fa fa-heart-o"></i>
              <span>23</span>
            </div>
            <div class="information-right">
              <i class="fa fa-commenting-o"></i>
              <span>0</span>
            </div>
          </div>
        </div>
        <div class="header">
          <a class="title">No Man's Sky</a>
        </div>
      </div>
    </section>

  </div>
</div>
</template>
<script>
import {getGamePlatformsAction} from '../../vuex/actions';

export default {
  vuex: {
    getters: {
      platformsStore: ({GamePlatforms}) => GamePlatforms.platforms
    },
    actions: {
      getGamePlatformsAction
    }
  },
  created() {
    if (this.platformsStore === null) {
      this.getGamePlatformsAction();
    }
  }
};
</script>
<style>
.game-banner{
  position:relative;
  margin-top: -20px;
  width: 100%;
  height: 400px;
  background-image: url('https://ooo.0o0.ooo/2016/08/01/579ee15ad1ad9.jpg');
  background-size: cover;
}

.game-banner .backdrop{
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-color: #1c1c1d;
  opacity: .2;
}

.game-banner .content{
  position: absolute;
  width: 1200px;
  margin: auto;
  top: 0; left: 0; bottom: 0; right: 0;
  padding-left: 1rem;
  padding-right: 1rem;
  height: 200px;
  border-radius: 4px;
  color: #fff;
}

.games{
  width: 1200px;
}
.platform-menus{
  list-style: none;
  margin:0;
  font-size:16px;
  text-align: center;
}
.platform-menus > a {
  display: inline-block;
  margin: 15px;
  padding: 5px;
  color: #888;
}
.platform-menus > a:hover {
  background-color: #FCDA05;
  color: #333;
}
.clearfix:after {

content: ".";

display: block;

height: 0;

clear: both;

visibility: hidden;

}
.game-items {
  margin-top: 54px;
  display: block;
}

.game-items .game-item .modal-thumbnail {
  width: 250px;
  height: 315px;
  overflow: hidden;
  display: block;
  position: relative;
}
.game-items .game-item .modal-thumbnail:hover{
/*    -webkit-filter: blur(4px);
  filter: blur(4px);*/
}
.game-items .game-item .modal-thumbnail .modal-thumbnail-overlay{
    display: block;
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: -webkit-linear-gradient(90deg,rgba(0,0,0,.6)0,transparent 30%);
    background: linear-gradient(0,rgba(0,0,0,.6)0,transparent 30%);
    opacity: .7;
    -webkit-transition: opacity .3s;
    transition: opacity .3s;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
}

.game-items .game-item .modal-thumbnail .modal-thumbnail-overlay:hover {
  background-color: #1c1c1d;
  opacity: .5;
  cursor: pointer;

}

.game-items .game-item .modal-thumbnail .modal-thumbnail-gameimage {
  height: 100%;
  background-position: center center;
  background-size: cover;
  border-top-left-radius: 10px;
   border-top-right-radius: 10px;
}

.game-items .game-item .header {
  padding: 15px 15px 11px;
}

.game-items .game-item .header .title,.game-items .game-item .header .meta {
  margin:0;
}

.game-items .game-item .header .title {
  font-size: 14px;
  font-weight: bold;
  color: #333;
}
.game-items .game-item .header .meta {
  font-size: 13px;
  color: #666;
}

.game-items .game-item {
  background-color: #fff;
  border-radius: 4px;
    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,0.2);
    -moz-box-shadow: 0 1px 2px rgba(0,0,0,0.2);
    -o-box-shadow: 0 1px 2px rgba(0,0,0,0.2);
    box-shadow: 0 1px 2px rgba(0,0,0,0.2);
}

.game-items .game-item {
  margin-bottom: 54px;
  float: left;
  display: block;
  margin-right: 4.99765%;
  width: 21.23176%;
  border-radius: 10px;
}

.game-items .game-item:nth-child(4n+1) {
  clear: left;
}

.game-items .game-item:nth-child(4n) {
    margin-right: 0;
}

.game-items .game-item:last-child {
    margin-right: 0;
}

.modal-thumbnail-information {
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: justify;
  -webkit-justify-content: space-between;
  -ms-flex-pack: justify;
  justify-content: space-between;
  position: absolute;
  margin-top: -34px;
  width: 100%;
  padding: 0 15px;
  text-shadow: 0 0 1px #000;
  color: #fff;
  opacity: .5;
  pointer-events: none;
  -webkit-transition: opacity .3s,color .2s;
  transition: opacity .3s,color .2s;
}

.game-items .game-item .modal-thumbnail:hover .modal-thumbnail-information{
 color: #FFF;
}

.modal-thumbnail-information {
  font-family: 'Open Sans',sans-serif;
  font-size: 14px;
  font-weight: bold;
  color: #F2F2F2;
}
</style>