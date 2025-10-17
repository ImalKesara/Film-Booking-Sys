<script lang="ts">
	import { Switch } from '@skeletonlabs/skeleton-svelte';

	let checked: boolean = $state(false);

	$effect(() => {
		const mode = localStorage.getItem('mode') || 'light';
		checked = mode === 'dark' ? true : false;
		console.log('Theme mode set to:', mode);
	});

	function onCheckedChange(event: { checked: boolean }) {
		const mode = event.checked ? 'dark' : 'light';
		document.documentElement.setAttribute('data-mode', mode);
	}
</script>

<svelte:head>
	<script>
		const mode = localStorage.getItem('mode') || 'light';
		document.documentElement.setAttribute('data-mode', mode);
	</script>
</svelte:head>

<Switch {checked} {onCheckedChange} name="mode"></Switch>
