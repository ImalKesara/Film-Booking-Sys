<script lang="ts">
	let { data } = $props();
</script>

<div class="table-wrap w-3xl">
	<table class="table caption-bottom">
		<caption class="pt-4">Revenue summary</caption>
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
				<td class="text-center">{data.summaries.length}</td>
			</tr>
		</tfoot>
	</table>
</div>
