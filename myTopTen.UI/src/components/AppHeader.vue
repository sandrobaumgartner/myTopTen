<template>
  <CHeader position="sticky" class="mb-4 header">
    <CContainer fluid>
      <CHeaderToggler
        class="ps-1 text-white"
        @click="$store.commit('toggleSidebar')"
      >
        <CIcon icon="cil-menu" size="lg" />
      </CHeaderToggler>
      <div class="searchbar">
        <input
          type="text"
          placeholder="Search user"
          v-bind:class="{
            roundEdges: searchBarRoundEdges,
            noRoundEdges: !searchBarRoundEdges,
          }"
          v-model="searchTerm"
          @input="search"
          @focus="search"
          @blur="empty"
        />
        <div class="suggestions" v-if="suggestions.length">
          <ul>
            <li
              v-for="suggestion in suggestions"
              :key="suggestion.id"
              v-on:click="routeToUserPage(suggestion)"
            >
              <span>{{ suggestion.username }}</span>
            </li>
          </ul>
        </div>
      </div>
      <CHeaderNav>
        <AppHeaderDropdownAccnt />
      </CHeaderNav>
    </CContainer>
  </CHeader>
</template>

<script>
import AppHeaderDropdownAccnt from './AppHeaderDropdownAccnt'
import { logo } from '@/assets/brand/logo'
export default {
  name: 'AppHeader',
  components: {
    AppHeaderDropdownAccnt,
  },
  setup() {
    return {
      logo,
    }
  },
  data() {
    return {
      suggestions: [],
      searchTerm: '',
    }
  },
  computed: {
    searchBarRoundEdges() {
      return this.suggestions.length <= 0
    },
  },
  methods: {
    search() {
      this.$store
        .dispatch('userManagement/getUserByPartOfUsername', this.searchTerm)
        .then((response) => {
          this.suggestions = response
        })
    },
    empty() {
      setTimeout(() => {
        this.suggestions = []
      }, 200)
    },
    routeToUserPage(searchedUser) {
      this.$router.push('/user/' + searchedUser.id)
    },
  },
}
</script>

<style scoped>
.searchbar {
  position: relative;
  display: flex;
  align-items: center;
  width: 70%;
}

.searchbar input[type='text'] {
  width: 100%;
  height: 40px;
  padding: 10px;
  border: none;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  font-size: 16px;
}

.roundEdges {
  border-radius: 20px;
}

.noRoundEdges {
  border-radius: 10px 10px 0 0;
}

.searchbar input[type='text']:focus {
  outline: none;
}

.suggestions {
  position: absolute;
  top: 40px;
  left: 0;
  z-index: 1;
  width: 100%;
  background-color: #fff;
  border-radius: 0 0 10px 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.suggestions ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.suggestions li {
  padding: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.suggestions li:hover {
  background-color: #f5f5f5;
}

.header {
  background-color: #303c54;
}
</style>
