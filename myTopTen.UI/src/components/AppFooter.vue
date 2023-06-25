<template>
  <CFooter class="center">
    <div>
      <span>myTopTen</span>
      <span class="ms-1"
        >&copy; {{ new Date().getFullYear() }}</span
      >
    </div>
  </CFooter>
</template>

<script>
export default {
  name: 'AppFooter',
}
</script>

<style scoped>
  .center {
    justify-content: center;
  }
</style>