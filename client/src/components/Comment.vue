<template>
  <p>{{ comment.user.username }}: {{ comment.content }}</p>
  <Comment class="margin" v-for="answer of comment.answers" :key="answer.id" :comment="answer"></Comment>
</template>

<script>
export default {
  name: "Comment",
  props: {
    comment: Object
  }
}
</script>

<style scoped>
.margin {
  margin-left: .5rem;
}
</style>