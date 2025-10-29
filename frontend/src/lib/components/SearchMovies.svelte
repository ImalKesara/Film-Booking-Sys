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

<input type="text" placeholder="Search" class="input max-w-sm rounded-none" bind:value={searchTerm} />
<button class="btn btn-sm uppercase rounded-none preset-filled-warning-500 text-white" onclick={searchMovies}>Search</button>
