<script lang="ts">
	import { LineChart, Axis } from 'layerchart';
	import { curveCatmullRom, curveLinearClosed } from 'd3-shape';

	let { data } = $props();

	const dateSeriesData = data.summaries.map((summary) => {
		return {
			date: new Date(summary.showDate),
			revenue: summary.totalRevenue
		};
	});

	const revenue = data.summaries.reduce((sum, item) => sum + item.totalRevenue, 0);
	console.log(revenue)
</script>

<section class="mx-auto grid max-w-6xl grid-cols-12">
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
	<div class="col-span-4 h-[300px] rounded-sm border p-4">
		<LineChart
			data={dateSeriesData}
			x="date"
			y="revenue"
			props={{ spline: { curve: curveCatmullRom } }}
			renderContext="svg"
		/>
	</div>
</section>
