<template>
  <div>Home</div>
  <CButton v-on:click="test">Register</CButton>
</template>

<script>
import router from '@/router'

export default {
  name: 'Home',
  methods: {
    test() {
      router.push('/pages/register')
    },
  },
}
</script>
