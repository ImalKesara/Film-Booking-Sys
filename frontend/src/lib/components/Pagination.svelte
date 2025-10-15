<script lang="ts">
	import { goto } from '$app/navigation';
	import { ArrowLeftIcon, ArrowRightIcon } from '@lucide/svelte';
	import { Pagination } from '@skeletonlabs/skeleton-svelte';

	let { currentPage, pageSize, totalResult, value = $bindable() } = $props();

	const PAGE_SIZE = pageSize;

	let page = $state(currentPage);
</script>

<div class="grid w-full place-items-center gap-4">
	<Pagination
		count={totalResult}
		pageSize={PAGE_SIZE}
		{page}
		onPageChange={(e) => {
			goto(`/admin?page=${e.page}`);
			page = e.page;
		}}
	>
		<Pagination.PrevTrigger>
			<ArrowLeftIcon class="size-4" />
		</Pagination.PrevTrigger>
		<Pagination.Context>
			{#snippet children(pagination)}
				{#each pagination().pages as page, index (page)}
					{#if page.type === 'page'}
						<Pagination.Item {...page}>
							{page.value}
						</Pagination.Item>
					{:else}
						<Pagination.Ellipsis {index}>&#8230;</Pagination.Ellipsis>
					{/if}
				{/each}
			{/snippet}
		</Pagination.Context>
		<Pagination.NextTrigger>
			<ArrowRightIcon class="size-4" />
		</Pagination.NextTrigger>
	</Pagination>
</div>
