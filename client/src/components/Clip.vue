<template>
  <div v-if="notFound === false" id="wrapper">
    <div id="mainContainer">
      <p id="caption">
        <router-link :to="'/user/' + user.username">@{{user.username}}</router-link><br/>
        {{ caption }}
      </p>
      <audio controls>
        <source :src="getAudioUrl" type="audio/ogg">
      </audio>
    </div>
    <div id="icons">
      <img @click="like" v-if="!liked" src="@/assets/heart.svg" alt="Heart">
      <img @click="like" v-else src="@/assets/heart_clicked.svg" alt="Heart">
      <p>{{ likes }}</p>
      <img @click="commentsExpanded = !commentsExpanded" src="@/assets/comment.svg" alt="Comment">
      <p>{{ comments }}</p>
    </div>
  </div>
  <div v-else-if="notFound === undefined"><h1 style="color: white">Loading...</h1></div>
  <div v-else><h1 style="color: white">Clip not found</h1></div>
  <Comments style="margin-left: 1rem" id="comments" v-if="commentsExpanded" :clipId="clipId"></Comments>
</template>

<script>
import Comments from "@/components/Comments";
export default {
  name: "Clip",
  components: {Comments},
  props: {
    clipId: Number
  },
  data() {
    return {
      caption: "",
      likes: 0,
      comments: 0,
      liked: false,
      user: "-",
      notFound: undefined,
      commentsExpanded: false
    }
  },
  mounted() {
    this.load();
  },
  methods: {
    load() {
      let req = new XMLHttpRequest();
      req.open("GET", window.location.origin + "/api/clip/" + this.clipId);
      let that = this;
      req.addEventListener("load", () => {
        if (req.status === 200) {
          let data = JSON.parse(req.responseText);
          that.caption = data.caption;
          that.likes = data.likes;
          that.comments = data.comments;
          that.liked = data.liked;
          that.user = data.user;
          that.notFound = false;
        } else
          that.notFound = true;
      });
      req.send();
    },
    like() {
      let req = new XMLHttpRequest();
      req.open("PATCH", window.location.origin + "/api/clip/" + this.clipId + "/like");
      let that = this;
      req.addEventListener("load", () => {
        if (req.responseText === "success")
          that.load();
      });
      req.send();
    }
  },
  computed: {
    getAudioUrl() {
      return window.location.origin + "/api/clip/" + this.clipId + "/audio"
    }
  }
}
</script>

<style scoped>
#mainContainer {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  background-color: #000000;
  min-height: 300px;
  min-width: 100px;
}

#wrapper {
  display: flex;
  align-items: flex-end;
  flex-wrap: nowrap;
  margin: 1rem 0;
}

#icons {
  margin-left: 1rem;
  color: white;
  text-align: center;
}

#icons p {
  margin: 0 auto;
}

#icons img {
  display: block;
  height: 75px;
}

#caption {
  color: white;
  margin-left: 1rem;
}

#comments {
  display: block;
}
</style>