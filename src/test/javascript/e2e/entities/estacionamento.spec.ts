import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Estacionamento e2e test', () => {

    let navBarPage: NavBarPage;
    let estacionamentoDialogPage: EstacionamentoDialogPage;
    let estacionamentoComponentsPage: EstacionamentoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Estacionamentos', () => {
        navBarPage.goToEntity('estacionamento');
        estacionamentoComponentsPage = new EstacionamentoComponentsPage();
        expect(estacionamentoComponentsPage.getTitle())
            .toMatch(/controleEstacionamentoApp.estacionamento.home.title/);

    });

    it('should load create Estacionamento dialog', () => {
        estacionamentoComponentsPage.clickOnCreateButton();
        estacionamentoDialogPage = new EstacionamentoDialogPage();
        expect(estacionamentoDialogPage.getModalTitle())
            .toMatch(/controleEstacionamentoApp.estacionamento.home.createOrEditLabel/);
        estacionamentoDialogPage.close();
    });

   /* it('should create and save Estacionamentos', () => {
        estacionamentoComponentsPage.clickOnCreateButton();
        estacionamentoDialogPage.setEntradaInput(12310020012301);
        expect(estacionamentoDialogPage.getEntradaInput()).toMatch('2001-12-31T02:30');
        estacionamentoDialogPage.setSaidaInput(12310020012301);
        expect(estacionamentoDialogPage.getSaidaInput()).toMatch('2001-12-31T02:30');
        estacionamentoDialogPage.setVagaInput('5');
        expect(estacionamentoDialogPage.getVagaInput()).toMatch('5');
        estacionamentoDialogPage.getEmUsoInput().isSelected().then((selected) => {
            if (selected) {
                estacionamentoDialogPage.getEmUsoInput().click();
                expect(estacionamentoDialogPage.getEmUsoInput().isSelected()).toBeFalsy();
            } else {
                estacionamentoDialogPage.getEmUsoInput().click();
                expect(estacionamentoDialogPage.getEmUsoInput().isSelected()).toBeTruthy();
            }
        });
        estacionamentoDialogPage.setVlrPagamentoInput('5');
        expect(estacionamentoDialogPage.getVlrPagamentoInput()).toMatch('5');
        estacionamentoDialogPage.veiculoSelectLastOption();
        estacionamentoDialogPage.patioSelectLastOption();
        estacionamentoDialogPage.save();
        expect(estacionamentoDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class EstacionamentoComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-estacionamento div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class EstacionamentoDialogPage {
    modalTitle = element(by.css('h4#myEstacionamentoLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    entradaInput = element(by.css('input#field_entrada'));
    saidaInput = element(by.css('input#field_saida'));
    vagaInput = element(by.css('input#field_vaga'));
    emUsoInput = element(by.css('input#field_emUso'));
    vlrPagamentoInput = element(by.css('input#field_vlrPagamento'));
    veiculoSelect = element(by.css('select#field_veiculo'));
    patioSelect = element(by.css('select#field_patio'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setEntradaInput = function(entrada) {
        this.entradaInput.sendKeys(entrada);
    }

    getEntradaInput = function() {
        return this.entradaInput.getAttribute('value');
    }

    setSaidaInput = function(saida) {
        this.saidaInput.sendKeys(saida);
    }

    getSaidaInput = function() {
        return this.saidaInput.getAttribute('value');
    }

    setVagaInput = function(vaga) {
        this.vagaInput.sendKeys(vaga);
    }

    getVagaInput = function() {
        return this.vagaInput.getAttribute('value');
    }

    getEmUsoInput = function() {
        return this.emUsoInput;
    }
    setVlrPagamentoInput = function(vlrPagamento) {
        this.vlrPagamentoInput.sendKeys(vlrPagamento);
    }

    getVlrPagamentoInput = function() {
        return this.vlrPagamentoInput.getAttribute('value');
    }

    veiculoSelectLastOption = function() {
        this.veiculoSelect.all(by.tagName('option')).last().click();
    }

    veiculoSelectOption = function(option) {
        this.veiculoSelect.sendKeys(option);
    }

    getVeiculoSelect = function() {
        return this.veiculoSelect;
    }

    getVeiculoSelectedOption = function() {
        return this.veiculoSelect.element(by.css('option:checked')).getText();
    }

    patioSelectLastOption = function() {
        this.patioSelect.all(by.tagName('option')).last().click();
    }

    patioSelectOption = function(option) {
        this.patioSelect.sendKeys(option);
    }

    getPatioSelect = function() {
        return this.patioSelect;
    }

    getPatioSelectedOption = function() {
        return this.patioSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
