<script lang="ts">
	interface Props {
		onsearch: (query: string) => void;
	}
	let { onsearch }: Props = $props();
	let searchTerm = $state('');

	function searchMovies() {
		onsearch(searchTerm);
	}
</script>

<input type="text" placeholder="Search Movies" class="input max-w-xl" bind:value={searchTerm} />
<button class="btn preset-filled-warning-500 text-white" onclick={searchMovies}>Search</button>
