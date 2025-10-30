<script lang="ts">
	import type { CalculateDiscountPrice } from '$lib/types';
	import { Dialog, Portal } from '@skeletonlabs/skeleton-svelte';
	let {
		paymentToggle = $bindable(),
		totalSeats,
		onConfirm,
		totalAmount
	}: {
		paymentToggle: boolean;
		totalSeats: number;
		onConfirm: () => Promise<void>;
		totalAmount: CalculateDiscountPrice | null;
	} = $props();
</script>

<Dialog open={paymentToggle} onOpenChange={(e) => (paymentToggle = e.open)}>
	<Portal>
		<Dialog.Backdrop class="bg-surface-50-950/50 fixed inset-0 z-50" />
		<Dialog.Positioner class="fixed inset-0 z-50 flex items-center justify-center">
			<Dialog.Content class="card bg-surface-100-900 w-full max-w-md space-y-4 p-6 shadow-xl">
				<Dialog.Title class="text-2xl font-bold">Payment Details</Dialog.Title>

				<!-- Booking Summary -->
				<div class=" space-y-2 rounded-lg p-4">
					<div class="flex justify-between text-sm">
						<span class="text-gray-400">Total Seats:</span>
						<span class="font-semibold">{totalSeats}</span>
					</div>
					<div class="flex justify-between text-sm">
						<span class="text-gray-400">Total price</span>
						<span class="font-semibold">Rs. {totalAmount?.originalAmount}</span>
					</div>
					<div class="flex justify-between text-sm">
						<span class="text-gray-400"
							>Discount Applied <span class="text-xs capitalize">(based on loyal points)</span
							></span
						>
						<span class="text-warning-500 font-semibold">Rs. -{totalAmount?.discountApplied}</span>
					</div>
					<hr class="hr" />
					<div class="flex justify-between">
						<span class="font-bold">Total Amount:</span>
						<span class="text-primary-500 text-xl font-bold"
							>Rs. {totalAmount?.discountedAmount}</span
						>
					</div>
				</div>

				<Dialog.Description class="space-y-4">
					<div>
						<label class="mb-1 block text-sm font-medium" for="cardNumber">Card Number</label>
						<input
							id="cardNumber"
							class="input"
							type="text"
							placeholder="1234 5678 9012 3456"
							maxlength="19"
						/>
					</div>

					<div class="grid grid-cols-2 gap-4">
						<div>
							<label class="mb-1 block text-sm font-medium" for="expiry">Expiry Date</label>
							<input id="expiry" class="input" type="text" placeholder="MM/YY" maxlength="5" />
						</div>
						<div>
							<label class="mb-1 block text-sm font-medium" for="cvv">CVV</label>
							<input id="cvv" class="input" type="text" placeholder="123" maxlength="3" />
						</div>
					</div>

					<div>
						<label class="mb-1 block text-sm font-medium" for="cardName">Cardholder Name</label>
						<input id="cardName" class="input" type="text" placeholder="John Doe" />
					</div>
				</Dialog.Description>

				<div class="flex gap-3">
					<button
						class="btn preset-filled-primary-500 flex-1"
						onclick={() => {
							paymentToggle = !paymentToggle;
							onConfirm();
						}}>Confirm Payment</button
					>
					<Dialog.CloseTrigger class="btn preset-tonal flex-1">Cancel</Dialog.CloseTrigger>
				</div>
			</Dialog.Content>
		</Dialog.Positioner>
	</Portal>
</Dialog>
