<template>
  <div id="root">
    <div>
      <Comment v-for="comment of comments" :key="comment.id" :comment="comment"></Comment>
    </div>
    <div>
      <textarea v-model="content" placeholder="Comment..."></textarea>
      <button @click="submit">Send</button>
    </div>
  </div>
</template>

<script>
import Comment from "@/components/Comment";
export default {
  name: "Comments",
  components: {Comment},
  props: {
    clipId: Number
  },
  data() {
    return {
      comments: [],
      content: ""
    }
  },
  mounted() {
    this.load();
  },
  methods: {
    load() {
      let req = new XMLHttpRequest();
      req.open("GET", window.location.origin + "/api/clip/" + this.clipId + "/comments");
      let that = this;
      req.addEventListener("load", () => {
        if (req.status === 200) {
          that.comments = JSON.parse(req.responseText);
        }
      });
      req.send();
    },
    submit() {
      let req = new XMLHttpRequest();
      req.open("POST", window.location.origin + "/api/clip/" + this.clipId + "/comments");
      let formData = new FormData();
      let that = this;
      formData.append("content", this.content)
      req.addEventListener("load", () => {
        if (req.status === 200) {
          that.load();
          that.content = "";
        } else
          alert("Comment could not be posted!")
      });
      req.send(formData);
    }
  }
}
</script>

<style scoped>
#root {
  min-height: 100%;
  background-color: white;
}
</style>