import { browser } from '$app/environment';

type User = {
	id: number;
	email: string;
	role: string;
	name: string;
};

class AuthState {
	token = $state<string | null>(null);
	user = $state<User | null>(null);
	isLoading = $state<boolean>(false);

	constructor() {
		if (browser) {
			this.init();
		}
	}

	async init() {
		const token = localStorage.getItem('token');
		if (token) {
			this.token = token;
			await this.fetchUser();
		}
	}

	async fetchUser() {
		if (!this.token) return;

		try {
			const response = await fetch('/api/auth/me', {
				headers: {
					Authorization: `Bearer ${this.token}`
				}
			});

			if (response.ok) {
				this.user = await response.json();
				console.log('fetched user', this.user);
			} else {
				this.logout();
			}
		} catch (error) {
			console.error('Error fetching user:', error);
			this.logout();
		}
	}

	login(token: string) {
		this.token = token;
		localStorage.setItem('token', token);
		this.fetchUser();
	}

	logout() {
		this.token = null;
		this.user = null;
		localStorage.removeItem('token');
	}

	// if token and user are null then not authenticated
	get isAuthenticated() {
		return !!this.token && !!this.user;
	}

	get isAdmin() {
		return this.user?.role === 'ADMIN';
	}
}

export const auth = new AuthState();
