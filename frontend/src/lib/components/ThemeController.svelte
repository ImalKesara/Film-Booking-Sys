<script lang="ts">
	import { MoonIcon, SunIcon } from '@lucide/svelte';

	let checked: boolean = $state(false);

	$effect(() => {
		const mode = localStorage.getItem('mode') || 'light';
		checked = mode === 'dark' ? true : false;
	});

	const toggle = () => {
		checked = !checked;
		const mode = checked ? 'dark' : 'light';
		document.documentElement.setAttribute('data-mode', mode);
		localStorage.setItem('mode', mode);
	};
</script>

<svelte:head>
	<script>
		const mode = localStorage.getItem('mode') || 'light';
		document.documentElement.setAttribute('data-mode', mode);
	</script>
</svelte:head>

<button class="btn-icon hover:preset-tonal" onclick={toggle}>
	{#if checked}
		<SunIcon class="size-5" />
	{:else}
		<MoonIcon class="size-5" />
	{/if}
</button>
