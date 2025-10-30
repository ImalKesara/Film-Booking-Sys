<script lang="ts">
	import { PieChart, AreaChart } from 'layerchart';
	import { curveCatmullRom } from 'd3-shape';
	import { CircleDollarSign, PiggyBank, TrendingUp, Film, Clapperboard } from '@lucide/svelte';
	import type { FilterPieValues } from '$lib/types.js';

	let { data } = $props();

	let selectedLocationId = $state(null);
	let filteredData: FilterPieValues[] = $state([]);
	let isLoading = $state(false);

	async function fetchRevenueByLocation(locationId: number) {
		const token = localStorage.getItem('token');
		isLoading = true;
		try {
			const response = await fetch(`/api/admin/pie-visualize/${locationId}`, {
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			});
			const newData = await response.json();
			filteredData = newData;
		} catch (error) {
			console.error('Failed to fetch revenue data:', error);
		} finally {
			isLoading = false;
		}
	}

	const dateSeriesData = data.allRevenue.map((revenue) => {
		return {
			date: revenue.revenueDate,
			revenue: revenue.totalRevenue
		};
	});

	const revenue = data.summaries.reduce((sum, item) => sum + item.totalRevenue, 0);
</script>

<section class="mx-auto max-w-7xl px-4 py-8">
	<!-- Header -->
	<div class="mb-8">
		<h1 class="text-surface-900-50 text-3xl font-bold">Dashboard Overview</h1>
		<p class="text-surface-600-400 mt-2">Track your cinema performance and revenue</p>
	</div>

	<div class="my-10">
		<div class="my-3 grid grid-cols-4 gap-4">
			<div class="card preset-filled-surface-100-900 w-full p-5 transition-shadow hover:shadow-lg">
				<div class="flex items-center justify-between">
					<p class="text-sm font-medium">Total Revenue</p>
					<div class="bg-primary-100-900 rounded-lg p-2">
						<PiggyBank class="h-5 w-5" />
					</div>
				</div>
				<p class="mt-3 text-2xl font-bold">LKR {revenue.toLocaleString('en-US')}</p>
				<p class="text-surface-600-400 mt-1 text-xs">All Time</p>
			</div>

			<div class="card preset-filled-surface-100-900 w-full p-5 transition-shadow hover:shadow-lg">
				<div class="flex items-center justify-between">
					<p class="text-sm font-medium">Daily Revenue</p>
					<div class="bg-secondary-100-900 rounded-lg p-2">
						<CircleDollarSign class="h-5 w-5" />
					</div>
				</div>
				<p class="mt-3 text-2xl font-bold">
					LKR {data.latestRevenue.totalRevenue.toLocaleString('en-US')}
				</p>
				<p class="text-surface-600-400 mt-1 text-xs">Today Revenue</p>
			</div>

			<div class="card preset-filled-surface-100-900 w-full p-5 transition-shadow hover:shadow-lg">
				<div class="flex items-center justify-between">
					<p class="text-sm font-medium">Total Movies</p>
					<div class="bg-success-100-900 rounded-lg p-2">
						<Clapperboard class="h-5 w-5" />
					</div>
				</div>
				<p class="mt-3 text-2xl font-bold">{data.totalMovies.length}</p>
				<p class="text-surface-600-400 mt-1 text-xs">Movies</p>
			</div>

			<div class="card preset-filled-surface-100-900 w-full p-5 transition-shadow hover:shadow-lg">
				<div class="flex items-center justify-between">
					<p class="text-sm font-medium">Total Movies</p>
					<div class="bg-error-100-900 rounded-lg p-2">
						<Clapperboard class="h-5 w-5" />
					</div>
				</div>
				<p class="mt-3 text-2xl font-bold">LKR {revenue.toLocaleString('en-US')}</p>
				<p class="text-surface-600-400 mt-1 text-xs">All Time</p>
			</div>
		</div>
	</div>

	<!-- Charts Section -->
	<div class="mb-10 grid grid-cols-1 gap-6 lg:grid-cols-3">
		<!-- Revenue Trend Chart -->
		<div class="card preset-filled-surface-50-900 overflow-hidden p-6 lg:col-span-2">
			<div class="mb-4">
				<h2 class="text-surface-900-50 text-xl font-bold">Revenue Trend</h2>
				<p class="text-surface-600-400 text-sm">Daily revenue over time</p>
			</div>
			<div class="chart-container">
				<AreaChart
					data={dateSeriesData}
					x="date"
					y="revenue"
					props={{ xAxis: { tickSpacing: 100 }, area: { curve: curveCatmullRom } }}
				/>
			</div>
		</div>

		<!-- Location Revenue Distribution -->
		<div class="card preset-filled-surface-50-900 overflow-hidden p-6">
			<div class="mb-4">
				<h2 class="text-surface-900-50 text-xl font-bold">By Location</h2>
				<p class="text-surface-600-400 text-sm">Hall distribution</p>
			</div>

			<label class="label mb-4">
				<span class="text-surface-700-300 text-sm font-medium">Select Location</span>
				<select
					class="select mt-2"
					bind:value={selectedLocationId}
					onchange={() => selectedLocationId && fetchRevenueByLocation(selectedLocationId)}
				>
					<option value={null}>Choose a location...</option>
					{#each data.locationData as location}
						<option value={location.locationId}>{location.name}</option>
					{/each}
				</select>
			</label>

			<div class="relative">
				{#if isLoading}
					<div
						class="bg-surface-50-900/80 absolute inset-0 flex items-center justify-center backdrop-blur-sm"
					>
						<div
							class="border-primary-500 h-8 w-8 animate-spin rounded-full border-4 border-t-transparent"
						></div>
					</div>
				{/if}
				<div class="h-[200px] overflow-hidden">
					{#if filteredData.length > 0}
						<PieChart data={filteredData} key="hall" value="movies" renderContext="svg" />
					{:else}
						<div class="text-surface-500-400 flex h-full items-center justify-center">
							<p class="text-center text-sm">Select a location to view distribution</p>
						</div>
					{/if}
				</div>
			</div>
		</div>
	</div>

	<!-- Revenue Table -->
	<div class="card preset-filled-surface-50-900 overflow-hidden">
		<div class="border-surface-200-800 border-b p-6">
			<h2 class="text-surface-900-50 text-xl font-bold">Revenue Breakdown</h2>
			<p class="text-surface-600-400 text-sm">Detailed show-by-show revenue</p>
		</div>

		<div class="overflow-x-auto">
			<table class="table w-full">
				<thead class="bg-surface-100-800">
					<tr>
						<th
							class="text-surface-700-300 px-6 py-4 text-left text-xs font-semibold uppercase tracking-wider"
							>Show ID</th
						>
						<th
							class="text-surface-700-300 px-6 py-4 text-left text-xs font-semibold uppercase tracking-wider"
							>Movie</th
						>
						<th
							class="text-surface-700-300 px-6 py-4 text-left text-xs font-semibold uppercase tracking-wider"
							>Hall</th
						>
						<th
							class="text-surface-700-300 px-6 py-4 text-left text-xs font-semibold uppercase tracking-wider"
							>Date</th
						>
						<th
							class="text-surface-700-300 px-6 py-4 text-left text-xs font-semibold uppercase tracking-wider"
							>Time</th
						>
						<th
							class="text-surface-700-300 px-6 py-4 text-center text-xs font-semibold uppercase tracking-wider"
							>Seats Booked</th
						>
						<th
							class="text-surface-700-300 px-6 py-4 text-right text-xs font-semibold uppercase tracking-wider"
							>Revenue</th
						>
					</tr>
				</thead>
				<tbody class="divide-surface-200-800 divide-y">
					{#each data.summaries as summary}
						<tr class="hover:bg-surface-100-800 transition-colors duration-150">
							<td class="text-surface-600-400 whitespace-nowrap px-6 py-4 text-sm font-medium"
								>#{summary.showId}</td
							>
							<td class="text-surface-900-50 px-6 py-4 text-sm font-medium">{summary.movieTitle}</td
							>
							<td class="text-surface-600-400 px-6 py-4 text-sm">{summary.hallName}</td>
							<td class="text-surface-600-400 whitespace-nowrap px-6 py-4 text-sm"
								>{summary.showDate}</td
							>
							<td class="text-surface-600-400 whitespace-nowrap px-6 py-4 text-sm"
								>{summary.showTime}</td
							>
							<td class="text-surface-600-400 whitespace-nowrap px-6 py-4 text-center text-sm"
								>{summary.totalSeatsBooked}</td
							>
							<td
								class="text-surface-900-50 whitespace-nowrap px-6 py-4 text-right text-sm font-semibold"
							>
								LKR {summary.totalRevenue.toLocaleString('en-US')}
							</td>
						</tr>
					{/each}
				</tbody>
				<tfoot class="bg-surface-100-800">
					<tr>
						<td colspan="6" class="text-surface-900-50 px-6 py-4 text-sm font-bold"
							>Total Revenue</td
						>
						<td
							class="text-surface-900-50 whitespace-nowrap px-6 py-4 text-right text-lg font-bold"
						>
							LKR {revenue.toLocaleString('en-US')}
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
</section>

<style>
	.chart-container {
		height: 320px;
		width: 100%;
	}

	:global(.lc-root-container) {
		--color-primary: hsl(200 100% 50%);
	}

	/* Custom scrollbar for table */
	.overflow-x-auto::-webkit-scrollbar {
		height: 8px;
	}

	.overflow-x-auto::-webkit-scrollbar-track {
		background: hsl(var(--color-surface-200));
	}

	.overflow-x-auto::-webkit-scrollbar-thumb {
		background: hsl(var(--color-surface-400));
		border-radius: 4px;
	}

	.overflow-x-auto::-webkit-scrollbar-thumb:hover {
		background: hsl(var(--color-surface-500));
	}
</style>
