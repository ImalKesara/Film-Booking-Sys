<script lang="ts">
	import Toaster from '$lib/components/Toaster.svelte';
	import { toaster } from '$lib/states/toaster.svelte';
	import '../app.css';
	let { children } = $props();
</script>

<Toaster {toaster} />
{@render children()}
