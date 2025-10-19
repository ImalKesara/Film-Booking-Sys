<script lang="ts">
	import '../../app.css';
	import favicon from '$lib/assets/favicon.svg';
	let { children } = $props();
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
</svelte:head>

<main class="flex h-svh items-center justify-center">
	{@render children()}
</main>
