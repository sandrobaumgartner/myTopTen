<template>
  <div>Home</div>
  <CButton v-on:click="test">Register</CButton>
</template>

<script>
import router from '@/router'

export default {
  name: 'Home',
  mounted() {
    this.$store.dispatch('userManagement/test')
  },
  methods: {
    test() {
      router.push('/pages/register')
    },
  },
}
</script>
