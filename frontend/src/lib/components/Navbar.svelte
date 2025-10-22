<script lang="ts">
	import { MenuIcon } from '@lucide/svelte';
	import { AppBar } from '@skeletonlabs/skeleton-svelte';
	import ThemeController from './ThemeController.svelte';
	import { auth } from '$lib/states/auth.svelte';
	import { goto } from '$app/navigation';
	import { Avatar } from '@skeletonlabs/skeleton-svelte';
	import { fullname } from '$lib/utils';

	const logout = () => {
		auth.logout();
		goto('/');
	};
</script>

<AppBar>
	<AppBar.Toolbar class="grid-cols-[1fr_2fr_1fr]">
		<AppBar.Lead>
			<button type="button" class="btn-icon btn-icon-lg hover:preset-tonal"><MenuIcon /></button>
		</AppBar.Lead>
		<AppBar.Headline class="flex justify-center gap-2">
			{#if auth.isAuthenticated}
				{#if auth.isAdmin}
				<a href="/admin/dashboard">Dashboard</a>
					<a href="/admin">Home</a>
					<a href="/admin/bucket">Bucket</a>
					<a href="/admin/slot">Slot</a>
				{/if}
			{/if}
		</AppBar.Headline>
		<AppBar.Trail class="items-center justify-end">
			<ThemeController />
			<!-- <button type="button" class="btn preset-filled-primary-500 rounded-none uppercase text-white"
				>buy tickets
			</button> -->
			{#if auth.isAuthenticated}
				<Avatar class="size-8 p-3">
					<Avatar.Fallback>{fullname(auth.user?.name)}</Avatar.Fallback>
				</Avatar>
				<button class="btn preset-filled-primary-500 uppercase" onclick={logout}>Login out</button>
			{:else}
				<a class="btn preset-tonal-surface uppercase" href="/login">login</a>
			{/if}
		</AppBar.Trail>
	</AppBar.Toolbar>
</AppBar>
