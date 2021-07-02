<template>
<div>
  <button @click="start">start</button>
  <button @click="stop">stop</button>
  <textarea v-model="caption" placeholder="Caption" maxlength="250"></textarea>
  <audio v-if="audioUrl !== null" controls>
    <source :src="audioUrl" type="audio/ogg">
  </audio>
  <button @click="submit">Upload</button>
</div>
</template>

<script>
export default {
  name: "Create",
  data() {
    return {
      audio: {
        mediaRecorder: null,
        chunks: [],
        blob: null,
      },
      caption: "",
      audioUrl: null
    }
  },
  mounted() {
    navigator.mediaDevices.getUserMedia({audio: true}).then(stream => {
      this.audio.mediaRecorder = new MediaRecorder(stream);
      let that = this;
      this.audio.mediaRecorder.ondataavailable = e => {
        that.audio.chunks.push(e.data);
      }

      this.audio.mediaRecorder.onstop = () => {
        that.audio.blob = new Blob(that.audio.chunks, { type: 'audio/ogg; codecs=opus' });
        that.audioUrl = window.URL.createObjectURL(that.audio.blob);
        console.log(that.audio.blob)
      }
    });
  },
  methods: {
    start() {
      this.audio.blob = null;
      this.audio.mediaRecorder.start();
    },
    stop() {
      this.audio.mediaRecorder.stop();
    },
    submit() {
      if (this.audio.blob !== null) {
        let formData = new FormData();
        formData.append("audio", this.audio.blob);
        formData.append("caption", this.caption);
        let req = new XMLHttpRequest();
        let that = this;
        req.open("POST", window.location.origin + "/api/clip");
        req.addEventListener("load", () => {
          if (req.status === 200) {
            alert("Upload successful!");
            that.audio.blob = null;
            that.audio.chunks = [];
            that.audioUrl = null;
          } else {
            alert("Upload failed");
          }
        })
        req.send(formData);
      } else
        alert("You need to record some audio!");
    }
  }
}
</script>

<style scoped>

</style>