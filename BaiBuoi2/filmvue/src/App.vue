<template>
  <div>
    <h1>Film List</h1>
    <FilmList :films="films" />
  </div>
</template>

<script>
import FilmList from "./components/FilmList.vue";
import axios from "axios";
export default {
  components: {
    FilmList,
  },
  data() {
    return {
      films: [],
      loading: false,
      error: null,
    };
  },
  methods: {
    async fetchFilms() {
      try {
        this.error = null;
        this.loading = true;
        const url = `http://localhost:8000/api/film`;
        const response = await axios.get(url);
        this.films = response.data;
      } catch (err) {
        console.log(err);
      }
      this.loading = false;
    },
  },
  mounted() {
    this.fetchFilms();
  },
};
</script>

<style>
#app {
  font-family: "Times New Roman", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2f754c;
  margin-top: 20px;
}
</style>
