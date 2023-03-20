<template>
  <CDropdown variant="nav-item">
    <CDropdownToggle placement="bottom-end" class="py-0" :caret="false">
      <CAvatar :src="avatar" size="md" />
    </CDropdownToggle>
    <CDropdownMenu class="p-0">
      <CDropdownItem class="dropdownItem">
        <CIcon icon="cil-user" /> Profile
      </CDropdownItem>
      <CDropdownItem v-on:click="logout" class="dropdownItem">
        <CIcon icon="cil-lock-locked" /> Logout
      </CDropdownItem>
    </CDropdownMenu>
  </CDropdown>
</template>

<script>
import avatar from '@/assets/images/avatars/user_icon.png'
import router from '@/router'

export default {
  name: 'AppHeaderDropdownAccnt',
  setup() {
    return {
      avatar: avatar,
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('userManagement/logout').then(() => {
        router.push('/login')
      })
    },
  },
}
</script>

<style scoped>
.dropdownItem {
  cursor: pointer;
}
</style>
