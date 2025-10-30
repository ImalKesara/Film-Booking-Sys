<script lang="ts">
	import { LineChart, Axis, BarChart, AreaChart, LinearGradient, Area } from 'layerchart';
	import { curveBasis, curveBumpX, curveCatmullRom, curveLinearClosed } from 'd3-shape';
	import { onMount, tick } from 'svelte';
	import { CircleDollarSign, Clapperboard, DollarSign, PiggyBank } from '@lucide/svelte';

	let { data } = $props();

	const dateSeriesData = data.allRevenue.map((revenue) => {
		return {
			date: revenue.revenueDate,
			revenue: revenue.totalRevenue
		};
	});

	const revenue = data.summaries.reduce((sum, item) => sum + item.totalRevenue, 0);
</script>

<section class="mx-auto max-w-6xl">
	<!-- Card section -->
	<div class="my-10">
		<div class="my-3 grid grid-cols-4 gap-x-3">
			<div class="card preset-filled-surface-100-900 w-full max-w-md p-4">
				<div class="flex items-center justify-between">
					<p>Total Revenue</p>
					<div
						class="bg-primary-100-900
			 rounded p-1"
					>
						<PiggyBank />
					</div>
				</div>
				<p class="text-xl font-bold">LKR {revenue.toLocaleString('en-US')}</p>
				<p>All Time</p>
			</div>

			<div class="card preset-filled-surface-100-900 w-full max-w-md p-4">
				<div class="flex items-center justify-between">
					<p>Daily Revenue</p>
					<div class="bg-secondary-100-900 rounded p-1">
						<CircleDollarSign />
					</div>
				</div>
				<p class="text-xl font-bold">
					LKR {data.latestRevenue.totalRevenue.toLocaleString('en-US')}
				</p>
				<p>Today Revenue</p>
			</div>

			<div class="card preset-filled-surface-100-900 w-full max-w-md p-4">
				<div class="flex items-center justify-between">
					<p>Total Movies</p>
					<div class="bg-secondary-100-900 rounded p-1">
						<Clapperboard />
					</div>
				</div>
				<p class="text-xl font-bold">LKR {revenue.toLocaleString('en-US')}</p>
				<p>All Time</p>
			</div>

			<div class="card preset-filled-surface-100-900 w-full max-w-md p-4">
				<div class="flex items-center justify-between">
					<p>Total Movies</p>
					<div class="bg-secondary-100-900 rounded p-1">
						<Clapperboard />
					</div>
				</div>
				<p class="text-xl font-bold">LKR {revenue.toLocaleString('en-US')}</p>
				<p>All Time</p>
			</div>
		</div>
	</div>

	<div class="chart">
		<AreaChart
			data={dateSeriesData}
			x="date"
			y="revenue"
			props={{ xAxis: { tickSpacing: 100 }, area: { curve: curveCatmullRom } }}
		/>
	</div>

	<div class="table-wrap col-span-8">
		<table class="table caption-bottom">
			<caption class="pt-4">Revenue Summaries</caption>
			<thead>
				<tr>
					<th>Show ID</th>
					<th>movieTitle</th>
					<th>hallName</th>
					<th>showDate</th>
					<th>showTime</th>
					<th>totalSeatsBooked</th>
					<th class="!text-right">totalRevenue</th>
				</tr>
			</thead>
			<tbody class="[&>tr]:hover:preset-tonal-primary">
				{#each data.summaries as summary}
					<tr>
						<td>{summary.showId}</td>
						<td>{summary.movieTitle}</td>
						<td>{summary.hallName}</td>
						<td>{summary.showDate}</td>
						<td>{summary.showTime}</td>
						<td>{summary.totalSeatsBooked}</td>
						<td>{summary.totalRevenue}</td>
					</tr>
				{/each}
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6">Total</td>
					<td class="text-start">{revenue}</td>
				</tr>
			</tfoot>
		</table>
	</div>
</section>

<style>
	.chart {
		height: 300px;
	}

	/* Global styles (or can use Tailwind)  */
	:global(.lc-root-container) {
		--color-primary: hsl(200 100% 50%);
	}
</style>
